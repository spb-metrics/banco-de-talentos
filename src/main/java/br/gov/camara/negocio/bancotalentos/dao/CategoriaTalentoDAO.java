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
import br.gov.camara.negocio.autenticacaoperfil.pojo.Grupo;
import br.gov.camara.negocio.bancotalentos.pojo.CategoriaTalento;
import br.gov.camara.negocio.exception.DAOException;
import br.gov.camara.negocio.util.Consulta;
import br.gov.camara.negocio.util.ConsultaComum;

/**
 * Classe DAO para a tabela CategoriaTalento
 */

public class CategoriaTalentoDAO extends DAO implements ConsultaComum
{
    // Inicializa o log
    private static Log log = LogFactory.getLog(CategoriaTalentoDAO.class);

    /**
     * Construtor sem argumentos
     *
     */
    public CategoriaTalentoDAO()
    {
        super("Categorias de talentos");
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
        String strConsulta = " FROM" + " CategoriaTalento categoriaTalento" +

        " ORDER BY" + " categoriaTalento.sequencialOrdenacao";

        return obter(strConsulta);

    }

    /**
     * Obt�m todos os registros dispon�veis para determinado grupo 
     * 
     * @param objGrupo Grupo a ser pesquisado
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public List obterTodosPorGrupo(Grupo objGrupo) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        String strConsulta = " SELECT" + " categoriaTalento" +

        " FROM" + " CategoriaTalento categoriaTalento" +

        " INNER JOIN" + " categoriaTalento.gruposCategoriaTalento gruposCategoriaTalento" +

        " WHERE" + " gruposCategoriaTalento.codigo = " + objGrupo.getCodigo() +

        " ORDER BY" + " categoriaTalento.sequencialOrdenacao";

        return obter(strConsulta);
    }

    /**
     * Obt�m todos os registros dispon�veis para consulta 
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public List obterDisponiveisParaConsulta() throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        String strConsulta = " SELECT" + " DISTINCT categoriaAtributoTalento.categoriaTalento" +

        " FROM" + " CategoriaAtributoTalento categoriaAtributoTalento" +

        // " WHERE" + " categoriaAtributoTalento.atributoTalento.tipoHTML.multiplicidade IN ('U', 'M')" +

                " ORDER BY"
                + " categoriaAtributoTalento.categoriaTalento.sequencialOrdenacao";

        return obter(strConsulta);
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

        String strConsulta = " FROM" + " CategoriaTalento categoriaTalento" +

        " WHERE " + "categoriaTalento.identificador = " + strChave;

        return (CategoriaTalento) obter(strConsulta).get(0);
    }

    /**
     * Obt�m o total de registros
     * 
     * @return List Contendo os POJOs representando os registro obtidos
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

        // Monta query
        String strConsulta = " SELECT COUNT(*)" +

        " FROM" + " CategoriaTalento categoriaTalento";

        // Recupera os dados
        return obterTotalRegistros(strConsulta);
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
        String strConsulta = " FROM" + " CategoriaTalento categoriaTalento" +

        " ORDER BY" + " categoriaTalento.nome ASC";

        // Recupera os dados
        return obterPorPagina(intNumeroPagina, intMaximoPagina, strConsulta);
    }

    /**
     * Obt�m os grupos dispon�veis para determinada categoria de talento
     * 
     * @param objCategoriaTalento Categoria de talento a ser verificada
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public List obterGruposDisponiveis(CategoriaTalento objCategoriaTalento) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        String strConsulta = " FROM"
                + " Grupo grupo"
                +

                " WHERE"
                + " grupo.codigo NOT IN"
                +

                " (SELECT categoriaTalento.gruposCategoriaTalento.elements"
                + " FROM"
                + " CategoriaTalento categoriaTalento"
                + " WHERE"
                + " categoriaTalento.identificador = "
                + objCategoriaTalento.getIdentificador()
                + ")"
                +

                " ORDER BY"
                + " grupo.descricao ASC";

        return obter(strConsulta);

    }

    /**
     * Obt�m os grupos dispon�veis para determinada categoria de talento
     * 
     * @param objCategoriaTalento Categoria de talento a ser verificada
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public List obterGruposSelecionados(CategoriaTalento objCategoriaTalento) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        String strConsulta = " FROM"
                + " Grupo grupo"
                +

                " WHERE"
                + " grupo.codigo IN"
                +

                " (SELECT categoriaTalento.gruposCategoriaTalento.elements"
                + " FROM"
                + " CategoriaTalento categoriaTalento"
                + " WHERE"
                + " categoriaTalento.identificador = "
                + objCategoriaTalento.getIdentificador()
                + ")"
                +

                " ORDER BY"
                + " grupo.descricao ASC";

        return obter(strConsulta);

    }

    /**
     * Obt�m o �ltimo sequencial de ordena��o
     *
     * @return int contendo o valor requerido
     * 
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public int obterUltimoSequencialOrdenacao() throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        //Declara��es
        int intRetorno = 0;

        // Monta query
        String strConsulta = " SELECT " + " MAX(categoriaTalento.sequencialOrdenacao)" +

        " FROM" + " CategoriaTalento categoriaTalento";

        // Consulta
        List lstResultado = obter(strConsulta);
        if (!lstResultado.isEmpty() && lstResultado.get(0) != null)
        {
            intRetorno = Integer.parseInt(String.valueOf(lstResultado.get(0)));
        }
        else
        {
            intRetorno = 1;
        }

        // Retorna
        return intRetorno;
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
    public String excluirImpl(Object objCategoriaTalento) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Retorna consulta de exclus�o
        return " FROM" + " CategoriaTalento categoriaTalento" +

        " WHERE" + " categoriaTalento.identificador = " + ((CategoriaTalento) objCategoriaTalento).getIdentificador();
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

            " FROM" + " CategoriaTalento categoriaTalento";

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
            String strConsulta = " FROM" + " CategoriaTalento categoriaTalento";
            if (!strFiltro.trim().equals(""))
            {
                strConsulta += " WHERE " + strFiltro;
            }
            strConsulta += " ORDER BY" + " categoriaTalento.nome ASC";

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
     * 
     * Verifica se a categoria tem atributos relacionados com forma��o de descri��o marcada
     * 
     * @param objCategoriaTalento Categoria de talento a ser verificada
     * 
     * @return boolean Com a verifica��o
     * 
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public boolean verificarExistenciaAtributoTalento(CategoriaTalento objCategoriaTalento) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        String strConsulta = " SELECT " + " COUNT(*)" +

        " FROM" + " CategoriaAtributoTalento categoriaAtributoTalento" +

        " WHERE" + " categoriaAtributoTalento.categoriaTalento.identificador = " + objCategoriaTalento.getIdentificador();

        // Retorna
        int intTotalRegistros = obterTotalRegistros(strConsulta);
        if (intTotalRegistros > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * 
     * Verifica se a categoria tem atributos relacionados com forma��o de descri��o marcada
     * 
     * @param objCategoriaTalento Categoria de talento a ser verificada
     * 
     * @return boolean Com a verifica��o
     * 
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public boolean verificarAtributosAssociadosComFormacaoDescricao(CategoriaTalento objCategoriaTalento) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        String strConsulta = " SELECT "
                + " COUNT(*)"
                +

                " FROM"
                + " CategoriaAtributoTalento categoriaAtributoTalento"
                +

                " WHERE"
                + " categoriaAtributoTalento.categoriaTalento.identificador = "
                + objCategoriaTalento.getIdentificador()
                + " AND categoriaAtributoTalento.formacaoDescricao = 'S'";

        // Retorna
        int intTotalRegistros = obterTotalRegistros(strConsulta);
        if (intTotalRegistros > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     *  M�todo que pede a inicializa��o da propriedade gruposCategoriaTalento, que � lazy
     * 
     * @param objCategoriaTalento Objeto que tem o atributo que vai ser recuperado
     * 
     * @see br.gov.camara.negocio.DAO#inicializarRelacionamento(java.lang.Object)
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public void inicializarGruposCategoriaTalento(CategoriaTalento objCategoriaTalento) throws DAOException
    {
        inicializarRelacionamento(objCategoriaTalento.getGruposCategoriaTalento());
    }

    /**
     *  M�todo que pede a inicializa��o da propriedade gruposCategoriaTalento, que � lazy
     * 
     * @param objCategoriaTalento Lista com os objetos que t�m o atributo que vai ser recuperado
     * 
     * @see br.gov.camara.negocio.DAO#inicializarRelacionamento(java.lang.Object)
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public void inicializarGruposCategoriaTalento(List lstCategoriasTalento) throws DAOException
    {
        Iterator itrCategoriasTalento = lstCategoriasTalento.iterator();
        while (itrCategoriasTalento.hasNext())
        {
            inicializarGruposCategoriaTalento((CategoriaTalento) itrCategoriasTalento.next());
        }
    }
}
