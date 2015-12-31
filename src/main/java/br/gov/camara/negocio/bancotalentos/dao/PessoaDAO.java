/****
 * Copyright 2000, 2001, 2002, 2003, 2004, 2005, 2006, 2007, 2008 C�mara dos Deputados, Brasil
 * 
 * Este arquivo � parte do programa TALENTOS - Banco de Talentos
 *
 * O TALENTOS � um software livre; voc� pode redistribu�-lo e/ou modific�-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); na vers�o 2 da Licen�a.
 *
 * Este programa � distribu�do na esperan�a que possa ser �til, mas SEM NENHUMA GARANTIA; sem uma garantia impl�cita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. Veja a Licen�a P�blica Geral GNU para maiores detalhes.
 * 
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU, sob o t�tulo "LICENCA.txt", junto com este programa, se n�o, escreva para a Funda��o do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
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
     * Obt�m todos os registros
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
     * Obt�m um registro a partir da chave
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
     * @param Object POJO representando o objeto a ser exclu�do
     * 
     * @return String Contendo a consulta de exclus�o 
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

        // Retorna consulta de exclus�o
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
     * Obt�m total de registros da consulta
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
        // Declara��es
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
     * M�todo utilizado para efetuar consultas gen�ricas, por p�gina
     * 
     * @param objConsulta Objeto de consulta contendo os par�metros necess�rios para montar a query
     * @param intNumeroPagina N�mero da p�gina a ser exibida
     * @param intMaximoPagina N�mero total de registros da p�gina
     * 
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public List consultar(Consulta objConsulta, int intNumeroPagina, int intMaximoPagina) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }
        // Declara��es
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
     *  M�todo que pede a inicializa��o da propriedade grupo, que � lazy
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
     *  M�todo que pede a inicializa��o da propriedade grupo, que � lazy
     * 
     * @param lstPessoas Lista com os objetos que t�m o atributo que vai ser recuperado
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
     *  M�todo que pede a inicializa��o da propriedade lotacoes, que � lazy
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
     *  M�todo que pede a inicializa��o da propriedade grupo, que � lazy
     * 
     * @param lstPessoas Lista com os objetos que t�m o atributo que vai ser recuperado
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
     * Obt�m os registros de determinada p�gina
     * 
     * @param int N�mero da p�gina a ser mostrada
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
     * Obt�m total de registros da consulta
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
        // Declara��es
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
      //essa � uma limitacao de alguns bancos de dados

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
