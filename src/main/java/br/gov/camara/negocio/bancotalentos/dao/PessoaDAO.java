/****
 * Copyright 2000, 2001, 2002, 2003, 2004, 2005, 2006, 2007, 2008 Câmara dos Deputados, Brasil
 * 
 * Este arquivo é parte do programa TALENTOS - Banco de Talentos
 *
 * O TALENTOS é um software livre; você pode redistribuí-lo e/ou modificá-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); na versão 2 da Licença.
 *
 * Este programa é distribuído na esperança que possa ser útil, mas SEM NENHUMA GARANTIA; sem uma garantia implícita de ADEQUAÇÃO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. Veja a Licença Pública Geral GNU para maiores detalhes.
 * 
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU, sob o título "LICENCA.txt", junto com este programa, se não, escreva para a Fundação do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 ***/

package br.gov.camara.negocio.bancotalentos.dao;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.gov.camara.exception.CDException;
import br.gov.camara.negocio.DAO;
import br.gov.camara.negocio.bancotalentos.pojo.Pessoa;
import br.gov.camara.negocio.exception.DAOException;
import br.gov.camara.negocio.util.Consulta;
import br.gov.camara.negocio.util.ConsultaComum;

/**
 * Classe DAO para a tabela SigespPessoal
 * 
 */
public class PessoaDAO extends DAO implements ConsultaComum
{
    // Inicializa o log
    private static Log log = LogFactory.getLog(PessoaDAO.class);

    /**
     * Construtor sem argumentos
     */
    public PessoaDAO()
    {
        super("Pessoal");
    }

    /**
     * Obtém todos os registros
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public List obterTodos() throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        String strConsulta = " FROM" + " Pessoa pessoa" +

        " ORDER BY" + " pessoa.nome ASC";

        return super.obter(strConsulta);
    }

    /**
     * Obtém um registro a partir da chave
     *
     * @param strChave Chave do registro a ser obtido
     * 
     * @return Object POJO representando o registro obtido
     *
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public Object obterPelaChave(String strChave) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        String strConsulta = " FROM" + " Pessoa pessoa" +

        " WHERE" + " pessoa.identificador = " + strChave;

        List lstPessoa = super.obter(strConsulta);
        if (lstPessoa == null)
        {
            return null;
        }
        else if (lstPessoa.size() == 0)
        {
            return null;
        }
        else
        {
            return lstPessoa.get(0);
        }
    }

    /**
     * Exclui um registro
     *
     * @param Object POJO representando o objeto a ser excluído
     * 
     * @return String Contendo a consulta de exclusão 
     * 
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public String excluirImpl(Object objPessoa) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Retorna consulta de exclusão
        return " FROM" + " Pessoa pessoa" + " WHERE" + " pessoa.identificador = " + ((Pessoa) objPessoa).getIdentificador();
    }

    /* (non-Javadoc)
     * @see br.gov.camara.negocio.util.ConsultaComum#consultar(br.gov.camara.negocio.util.Consulta)
     */
    public List consultar(Consulta objConsulta) throws DAOException
    {
        return null;
    }

    /**
     * Obtém total de registros da consulta
     *
     * @param Consulta objeto contendo os filtros para consulta
     *  
     * @return List contendo o resultado da pesquisa
     * 
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public int obterTotalRegistros(Consulta objConsulta) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }
        // Declarações
        int intRetorno = 0;

        try
        {
            String strFiltro = objConsulta.montarFiltro();

            // Monta query
            String strConsulta = " SELECT " + " COUNT(*)" +

            " FROM" + " Pessoa pessoa";

            if (!strFiltro.trim().equals(""))
            {
                strConsulta += " WHERE " + strFiltro;
            }

            // Recupera os dados
            intRetorno = super.obterTotalRegistros(strConsulta);

            // Retorna
            return intRetorno;
        }
        catch (CDException cde)
        {
            throw new DAOException("Ocorreu um erro ao consultar registros na classe " + strNomeClasse, cde);
        }
    }

    /**
     * Método utilizado para efetuar consultas genéricas, por página
     * 
     * @param objConsulta Objeto de consulta contendo os parâmetros necessários para montar a query
     * @param intNumeroPagina Número da página a ser exibida
     * @param intMaximoPagina Número total de registros da página
     * 
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public List consultar(Consulta objConsulta, int intNumeroPagina, int intMaximoPagina) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }
        // Declarações
        List lstRetorno = null;

        try
        {
            String strFiltro = objConsulta.montarFiltro();

            // Monta query
            String strConsulta = " FROM" + " Pessoa pessoa";
            if (!strFiltro.trim().equals(""))
            {
                strConsulta += " WHERE " + strFiltro;
            }
            strConsulta += " ORDER BY" + " pessoa.nome AS";

            // Recupera os dados 
            lstRetorno = obterPorPagina(intNumeroPagina, intMaximoPagina, strConsulta);

            // Retorna
            return lstRetorno;
        }
        catch (CDException cde)
        {
            throw new DAOException("Ocorreu um erro ao consultar registros na classe " + strNomeClasse, cde);
        }
    }

    /**
     *  Método que pede a inicialização da propriedade grupo, que é lazy
     * 
     * @param objPessoa Objeto que tem o atributo que vai ser recuperado
     * 
     * @see br.gov.camara.negocio.DAO#inicializarRelacionamento(java.lang.Object)
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public void inicializarGrupo(Pessoa objPessoa) throws DAOException
    {
        inicializarRelacionamento(objPessoa.getGrupo());
    }

    /**
     *  Método que pede a inicialização da propriedade grupo, que é lazy
     * 
     * @param lstPessoas Lista com os objetos que têm o atributo que vai ser recuperado
     * 
     * @see br.gov.camara.negocio.DAO#inicializarRelacionamento(java.lang.Object)
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public void inicializarGrupo(List lstPessoas) throws DAOException
    {
        Iterator itrPessoas = lstPessoas.iterator();
        while (itrPessoas.hasNext())
        {
            inicializarGrupo((Pessoa) itrPessoas.next());
        }
    }

    /**
     *  Método que pede a inicialização da propriedade lotacoes, que é lazy
     * 
     * @param objPessoa Objeto que tem o atributo que vai ser recuperado
     * 
     * @see br.gov.camara.negocio.DAO#inicializarRelacionamento(java.lang.Object)
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public void inicializarLotacoes(Pessoa objPessoa) throws DAOException
    {
        inicializarRelacionamento(objPessoa.getLotacoes());
    }

    /**
     *  Método que pede a inicialização da propriedade grupo, que é lazy
     * 
     * @param lstPessoas Lista com os objetos que têm o atributo que vai ser recuperado
     * 
     * @see br.gov.camara.negocio.DAO#inicializarRelacionamento(java.lang.Object)
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public void inicializarLotacoes(List lstPessoas) throws DAOException
    {
        Iterator itrPessoas = lstPessoas.iterator();
        while (itrPessoas.hasNext())
        {
            inicializarLotacoes((Pessoa) itrPessoas.next());
        }
    }

    /**
     * Obtém os registros de determinada página
     * 
     * @param int Número da página a ser mostrada
     * @param int Quantidade de registros
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public List obterPorPagina(int intNumeroPagina, int intMaximoPagina) throws DAOException
    {
        // Monta consulta
        String strConsulta = " FROM" + " Pessoa pessoa" +

        " ORDER BY" + " pessoa.identificador ASC";

        // Recupera os dados
        return obterPorPagina(intNumeroPagina, intMaximoPagina, strConsulta);
    }

    /**
     * Obtém total de registros da consulta
     *
     * @param Consulta objeto contendo os filtros para consulta
     *  
     * @return List contendo o resultado da pesquisa
     * 
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public int obterTotalRegistros() throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }
        // Declarações
        int intRetorno = 0;

        try
        {
            // Monta query
            String strConsulta = " SELECT COUNT(*)" + " FROM" + " Pessoa pessoa";

            // Recupera os dados
            intRetorno = obterTotalRegistros(strConsulta);

            // Retorna
            return intRetorno;
        }
        catch (CDException cde)
        {
            throw new DAOException("Ocorreu um erro ao consultar registros na classe " + strNomeClasse, cde);
        }
    }

    public List obterPorChaves(String[] listaDeChaves) throws DAOException
    {
      if (log.isDebugEnabled())
      {
        log.debug("Entrada no metodo");
      }

      //deve-se que criar clausulas in com no maximo 1000 elementos 
      //essa é uma limitacao de alguns bancos de dados

      int numIteracoes = listaDeChaves.length / 500; //numero de vezes que vai iterar
      int resto = listaDeChaves.length % 500; //pode ser que exija mais uma iteracao
      StringBuffer clausulaIn = new StringBuffer();

      clausulaIn.append(" pessoa.identificador in ( ");
      int i = 0;
      for (int j = 0; j < numIteracoes; j++)
      {
        for (int k = 0; k < 500; k++) //500 elementos
        {
          clausulaIn.append(listaDeChaves[i] + ",");
          i++;
        }

        clausulaIn.replace(clausulaIn.length() - 1, clausulaIn.length(), ")"); //remove a ultima virgula

        clausulaIn.append(" or pessoa.identificador in ( ");
      }

      if (resto > 0) //ainda existem elementos 
      {
        for (int k = 0; k < resto; k++)
        {
          clausulaIn.append(listaDeChaves[i] + ",");
          i++;
        }
        clausulaIn.replace(clausulaIn.length() - 1, clausulaIn.length(), ")"); //remove a ultima virgula

      } else //trata o ultimo "or pessoa.identificador in ("
      {
        clausulaIn.append("0)");
      }

      String strConsulta = " FROM" + " Pessoa pessoa"
              + " WHERE " + clausulaIn.toString();

      return super.obter(strConsulta);
    }
}
