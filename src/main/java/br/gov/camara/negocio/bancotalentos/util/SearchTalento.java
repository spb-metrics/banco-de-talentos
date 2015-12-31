/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.camara.negocio.bancotalentos.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import br.gov.camara.biblioteca.util.CaminhoAbsoluto;
import br.gov.camara.biblioteca.util.ClasseDinamica;
import br.gov.camara.exception.CDException;
import br.gov.camara.negocio.bancotalentos.facade.PessoaFacade;
import br.gov.camara.negocio.bancotalentos.pojo.Pessoa;

/**
 *
 * @author suporte-urms
 */
public class SearchTalento
{

  private Analyzer analyzer = null;
  
  /*
   * Deveria compartilhar o IndexSeacher mas achei melhor por enquanto sempre criar um novo.
   * Assim garanto que sempre a leitura se dará no arquivo de indice mais recentemente comitado.
   */
  
  // private static IndexSearcher searcher = null;
  private static QueryParser parser = null;
  private static Directory dir = null;
  private static String path = null; //indica o caminho para o arquivo de indice  
  private static final String LUCENE_INDEX_DIR = "Lucene.dir";//eh a chave a ser buscada no arquivo de properies
  private static final String CAMPO_CONTEUDO = "descricao_talento";
  private static Log log = LogFactory.getLog(SearchTalento.class);
  private static final SearchTalento instance = new SearchTalento();

  /** Creates a new instance of SearchEngine */
  private SearchTalento()
  {
    //Recursos compartilhados  
    analyzer = new StandardAnalyzer(Version.LUCENE_36);
    parser = new MultiFieldQueryParser(Version.LUCENE_36, new String[]
            {
              CAMPO_CONTEUDO
            }, analyzer);

  }

  public static SearchTalento getInstance() throws IOException
  {
    //É necessario tratar excecoes por isso a criacao desses objetos aqui e nao no construtor
    try
    {

      if (path == null)
      {

        //vai retornar o caminho absoluto ate o WEB_INF/classes
        //path = (new ClasseDinamica()).getClass().getClassLoader().getResource("resources").toURI().toString();        
        //path = path.replaceFirst("classes/resources", "");        
        //acrescenta o diretorio permanente de indice
        //path = path + "config" + File.separator + LUCENE_INDEX_DIR; //WEB_INF/config/index_lucene

        String referencia = lerArquivoConfiguracao().getProperty(LUCENE_INDEX_DIR);

        path = CaminhoAbsoluto.obter(referencia);

        File file = new File(path);
        if (!file.exists() || !file.isDirectory() || !file.canWrite())
        {
          throw new IOException("Diretório do indice não está acessível!");
        }

        
      }

      if (dir == null)
      {
        //Cria o diretorio    
        dir = FSDirectory.open(new File(path));
      }

      /* if (searcher == null)
      {
      searcher = new IndexSearcher(IndexReader.open(dir));
      }*/


    } catch (Exception e)
    {

      throw new IOException("Não foi possível inicializar SearchTalento! " + e.getMessage());
    }

    return instance;
  }

  public Document getDocument(String chave) throws IOException
  {
    IndexSearcher searcher = null;
    try
    {
      TermQuery term = new TermQuery(new Term("matricula", chave));

      searcher = new IndexSearcher(IndexReader.open(dir));

      TopDocs doc = searcher.search(term, 1);

      if (doc != null && doc.scoreDocs.length > 0)
      {
        return searcher.doc(doc.scoreDocs[0].doc);
      }
    } finally
    {
      if (searcher != null)
      {
        searcher.close();
      }
    }

    return null;
  }

  public List performSearch(String queryString) throws IOException, ParseException, CDException
  {

    PessoaFacade pessoaFacade = new PessoaFacade();
    
    //Assim nao considera quando o termo de pesquisa vem entre aspas
    //String[] criterios = queryString.split(" ");  
    
    
    ArrayList<String> termos = new ArrayList<String>();//cria uma lista com termos para pesquisa
    //Extraindo termos para filtro
    char[] query = queryString.toCharArray();
    //Itera nos caracteres buscando por aspas ou espacos em branco
    for(int i = 0; i < query.length; i++)
    {
      StringBuilder termo = new StringBuilder(); //monta termo a termo para depois adicionar na lista

      if(query[i] == '\"' || query[i] == '\'')
      {
        termo.append(query[i]);
        //continua iterando até achar o fecha aspas        
        i++;
        while(i < query.length && query[i] != '\"' && query[i] != '\'')
        {
          termo.append(query[i]);
          i++;
        }
        termo.append(query[i]);//inclui o fecha aspas
        //acabou de achar um termo que foi digitado entre aspas
        termos.add(termo.toString());
        termo = null;
      }
      else if(query[i] == ' ')
      {
        termo = null;
        continue;        
      }
      else
      {
        termo.append(query[i]);
        i++;
        while(i < query.length && query[i] != ' ')
        {
          termo.append(query[i]);
          i++;
        }
        //acabou de achar um termo 
        termos.add(termo.toString());
        termo = null;
      }
      
    }    
    
    
    BooleanQuery finalQuery = new BooleanQuery();
    IndexSearcher searcher = null;
    List<Pessoa> lstPessoas = new ArrayList();

    try
    {
      searcher = new IndexSearcher(IndexReader.open(dir));

      for (String s : termos)
      {
        /*
         * The BooleanClause.Occur.MUST ensures that all term queries must produce a match in order 
         * for the document to be a hit for the search. The purpose of the term.Replace("~", "") 
         * + "~" code is to add a tilde to the end of the search term so Lucene knows to perform a 
         * fuzzy search with this term. We must remove any tildes the user entered in the search 
         * string to prevent an exception that will occur if a search term ends with two tildes 
         * (e.g. ?John~~?).
         * 
         */
        
        if(s.startsWith("\""))    
        {
          /*s = s.replaceAll("\"", "");
          String aux[] = s.split(" ");
          PhraseQuery phrase = new PhraseQuery();
    
          for(String termPhrase : aux)
          {
            phrase.add(new Term(CAMPO_CONTEUDO,termPhrase.replaceAll("~", "")));
          }
          
          finalQuery.add(phrase, BooleanClause.Occur.MUST);*/
          
          
          //tanto faz qualquer um dos codigos entao deixei esse
          
          
          finalQuery.add(parser.parse(s), BooleanClause.Occur.MUST);
        }
        else
          finalQuery.add(parser.parse(s.replaceAll("~", "") + "~"), BooleanClause.Occur.MUST);
      }

      //Query query = parser.parse(criterios.toString());
      TopDocs hits = searcher.search(finalQuery, 1000);//retorna os top 1000 hits

      //obtem a lista das matriculas dos curriculos retornados    
      String[] matriculas = new String[hits.scoreDocs.length];
      for (int i = 0; i < hits.scoreDocs.length; i++)
      {
        matriculas[i] = searcher.doc(hits.scoreDocs[i].doc).get("matricula");
      }
      
      //obtem os usuarios com as respectivas matriculas
      for (int i = 0; i < matriculas.length; i++)
      {
        Pessoa pessoa = pessoaFacade.obterPelaChave(matriculas[i]);
        
        // TODO Confirmar com Christian se a linha abaixo pode ser comentada sem problemas 
        // pessoaFacade.configurarAdapterPessoa(pessoa);
        
        lstPessoas.add(pessoa); //insere na mesma ordem de relevancia retornada pelo Lucene
      }
    } finally
    {
      if(searcher != null)
        searcher.close();
    }

    return lstPessoas;
  }

  public Analyzer getAnalyzer()
  {
    return analyzer;
  }

  public Directory getDirectory()
  {
    return dir;
  }

  public String getPath()
  {
    return path;
  }

 

  private static Properties lerArquivoConfiguracao() throws IOException
  {
    if (log.isDebugEnabled())
    {
      log.debug("Entrada no metodo");
    }

    InputStream input;
    Properties propriedades = new Properties();

    input = (new ClasseDinamica()).obterClassLoader().getResourceAsStream("resources/Lucene.properties");
    if (input == null)
    {
      throw (new FileNotFoundException("Não foi possível encontrar o arquivo Lucene.properties"));
    }
    propriedades.load(input);

    return (propriedades);
  }
}