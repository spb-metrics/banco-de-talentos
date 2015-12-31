/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.camara.negocio.bancotalentos.util;

import br.gov.camara.biblioteca.util.ClasseDinamica;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.util.Version;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.index.IndexNotFoundException;
import org.apache.lucene.index.LogDocMergePolicy;

/**
 *
 * Num processamento em batch o IndexWriter podera ser usado varias vezes antes de ser fechado. 
 * A obrigacao de fechar é de quem usa a classe IndexaTalento justamente por isso.
 *   
 *
 * @author suporte-urms
 */
public class IndexaTalento 
{
  // Inicializa o log
  private static Log log = LogFactory.getLog(IndexaTalento.class);
  
  private static SearchTalento searcher = null;
 
  private IndexWriter indexWriter = null;
  //private IndexWriterConfig conf; nao pôde ficar compartilhado pois deve ser criado a cada execucao do Indexwriter
  
  
  //quero compartilhar alguns recursos nessa classe, por isso o singleton
  //quero controlar a utilizacao de uma instancia de IndexWriter para aproveita-la o maximo antes de ter que fecha-la
  private static IndexaTalento instance = new IndexaTalento();

  private IndexaTalento() 
  {
    
  }

  public Analyzer getAnalyzer()
  {
    return searcher.getAnalyzer();
  }

  public static String getPath()
  {
    return searcher.getPath();
  }

  public static Directory getDirectory()
  {
    return searcher.getDirectory();
  }

  public static IndexaTalento getInstance() throws IOException 
  {
    searcher = SearchTalento.getInstance();    
    
    return instance;
  }

  /*
   * Acrescenta um talento ao documento que representa os talentos de um usuario.
   * 
   * Nenhum metodo precisou ser serializado já que a classe IndexWriter é ThreadSafe
   */
  public void addTalento(String chave, String talento) throws IOException
  {

    this.createIndexWriter(); //cria o index writer
    
    //Procura documento para ver se já existe
    Document doc = null;
    try
    {      
      doc = searcher.getDocument(chave);
    } catch (IndexNotFoundException e)
    {
      //é a primeira execucao e o indice ainda nao existe, entao nao faz nada      
    }
    

    if (doc == null)
    {
      doc = new Document();

      //Insere campo chave para recuperacao de um documento especifico 
      //VEja texto explicativo:

      //How do I delete documents from the index?
      //IndexWriter allows you to delete by Term or by Query. The deletes are buffered and then periodically flushed to the index, and made visible once commit() or close() is called.
      //IndexReader can also delete documents, by Term or document number, but you must close any open IndexWriter before using IndexReader to make changes (and, vice/versa). IndexReader also buffers the deletions and does not write changes to the index until close() is called, but if you use that same IndexReader for searching, the buffered deletions will immediately take effect. Unlike IndexWriter's delete methods, IndexReader's methods return the number of documents that were deleted.
      //Generally it's best to use IndexWriter for deletions, unless 1) you must delete by document number, 2) you need your searches to immediately reflect the deletions or 3) you must know how many documents were deleted for a given deleteDocuments invocation.
      //If you must delete by document number but would otherwise like to use IndexWriter, one common approach is to make a primary key field, that holds a unique ID string for each document. Then you can delete a single document by creating the Term containing the ID, and passing that to IndexWriter's deleteDocuments(Term) method.
      //Once a document is deleted it will not appear in TermDocs nor TermPositions enumerations, nor any search results. Attempts to load the document will result in an exception. The presence of this document may still be reflected in the docFreq statistics, and thus alter search scores, though this will be corrected eventually as segments containing deletions are merged.      

      //E esse:
      //What happens when you IndexWriter.add() a document that is already in the index? Does it overwrite the previous document?
      //No, there will be multiple copies of the same document in the index.      


      //Insere campo para recuperacao do documento. Este será a matricula jah que em ultima instancia esta se buscando pelas pessoas 
      //cujos curriculos batem com a busca
      doc.add(new Field("matricula", chave, Field.Store.YES, Field.Index.NOT_ANALYZED));//nao eh para indexar por esse valor ja que nao fara parte da busca do usuario  

      //ANALYZED - Index the tokens produced by running the field's value through an Analyzer.
      //ANALYZED_NO_NORMS - Expert: Index the tokens produced by running the field's value through an Analyzer, and also separately disable the storing of norms.
      //NO - Do not index the field value.
      //NOT_ANALYZED - Index the field's value without using an Analyzer, so it can be searched.
      //NOT_ANALYZED_NO_NORMS - Expert: Index the field's value without an Analyzer, and also disable the storing of norms.          

      doc.add(new Field("descricao_talento", talento, Field.Store.YES, Field.Index.ANALYZED));


    } else //documento ja existia
    {
      //retorna uma string com todos os talentos concatenados
      StringBuilder descricao_talento = new StringBuilder(doc.get("descricao_talento"));
      descricao_talento.append(" " + talento);
      doc.removeField("descricao_talento");
      doc.add(new Field("descricao_talento", descricao_talento.toString(), Field.Store.YES, Field.Index.ANALYZED));
    }

    //insere um novo ou atualiza um existente   
    indexWriter.updateDocument(new Term("matricula", chave), doc);
    indexWriter.commit();
  }

  /*
   * Remove um talento especifico do documento de um usuario.
   * 
   */
  public void removeTalento(String chave, String talento) throws IOException
  {
    this.createIndexWriter();
   
    //obtem o doc do qual o talento deve ser excluido
    Document doc = searcher.getDocument(chave);
    if (doc != null)
    {
      String descricao_talento = (doc.get("descricao_talento"));

      descricao_talento = descricao_talento.replaceAll(talento, " ");
      doc.removeField("descricao_talento");
      doc.add(new Field("descricao_talento", descricao_talento, Field.Store.YES, Field.Index.ANALYZED));
      indexWriter.updateDocument(new Term("matricula", chave), doc);
      indexWriter.commit();
    }

  }

  /*
   * Acrescenta um documento inteiro de um usuario. Subentende-se que a string documento contém 
   * todas as informações necesárias para compor o documento (curriculo) de um usuario.
   * 
   * Nenhum metodo precisou ser serializado já que a classe IndexWriter é ThreadSafe
   */
  public void addDocumento(String chave, String documento) throws IOException
  {
   
    this.removeDocumento(chave);
    
    this.addTalento(chave, documento);
    
  }

  public void removeDocumento(String chave) throws IOException
  {
    this.createIndexWriter();
    indexWriter.deleteDocuments(new Term("matricula", chave));
    indexWriter.commit();
  }

  public void limparIndice() throws IOException
  {
    this.createIndexWriter();
    indexWriter.deleteAll();
    indexWriter.commit();
  }


  /*
   * Cada metodo dessa classe que usa o IndexWriter precisa antes verificar se o mesmo nao foi fechado pelo cliente
   * da classe em alguma outra execucao.
   */
  
  private void createIndexWriter() throws IOException
  {
    if (indexWriter == null)
    {
      //cria o ponteiro para o indice
      try
      {
        //cria uma configuracao preparada para criar ou estender um indice
        IndexWriterConfig cfg = new IndexWriterConfig(Version.LUCENE_36, searcher.getAnalyzer()).setOpenMode(OpenMode.CREATE_OR_APPEND);
        
        //dependendo da politica de indexacao podem ser abertos muitos arquivos simultaneamente
        //isso pode ocorrer em erro de runtime de FileNotFoundException
        //para minimizar a abertura desses arquivos, deve ser setado Use Compound File para true
        //um efeito negativo disso é que a indexação fica mais lenta
        LogDocMergePolicy logMergePolicy = new LogDocMergePolicy();
        logMergePolicy.setUseCompoundFile(true);
        cfg.setMergePolicy(logMergePolicy);
        
        //O IndexWriterConfig deve ser criado unicamente para cada instancia de IndexWriter
        indexWriter = new IndexWriter(searcher.getDirectory(), cfg);
        
      } catch (CorruptIndexException e)
      {
        throw new IOException(e.getMessage() + "Não foi possível indexar o documento porque o índice do Lucene está corrompido. ");
      } catch (LockObtainFailedException e)
      {
        throw new IOException(e.getMessage() + "Não foi possível obter o lock no arquivo de índice do Lucene.");
      } catch (IOException e)
      {
        throw new IOException(e.getMessage() + "Problema ao indexar no Lucene.");
      }
    }

  }

  
  public void close() throws IOException
  {
    if (indexWriter != null)
    {
      indexWriter.close();
      indexWriter = null;
      //dir.close();
    }
  }
}