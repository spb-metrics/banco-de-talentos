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
        String strConsulta = " FROM" + " CategoriaTalento categoriaTalento" +

        " ORDER BY" + " categoriaTalento.sequencialOrdenacao";

        return obter(strConsulta);

    }

    /**
     * Obtém todos os registros disponíveis para determinado grupo 
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
     * Obtém todos os registros disponíveis para consulta 
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

        String strConsulta = " FROM" + " CategoriaTalento categoriaTalento" +

        " WHERE " + "categoriaTalento.identificador = " + strChave;

        return (CategoriaTalento) obter(strConsulta).get(0);
    }

    /**
     * Obtém o total de registros
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
        String strConsulta = " FROM" + " CategoriaTalento categoriaTalento" +

        " ORDER BY" + " categoriaTalento.nome ASC";

        // Recupera os dados
        return obterPorPagina(intNumeroPagina, intMaximoPagina, strConsulta);
    }

    /**
     * Obtém os grupos disponíveis para determinada categoria de talento
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
     * Obtém os grupos disponíveis para determinada categoria de talento
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
     * Obtém o último sequencial de ordenação
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

        //Declarações
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
     * @param Object POJO representando o objeto a ser excluído
     * 
     * @return String Contendo a consulta de exclusão 
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

        // Retorna consulta de exclusão
        return " FROM" + " CategoriaTalento categoriaTalento" +

        " WHERE" + " categoriaTalento.identificador = " + ((CategoriaTalento) objCategoriaTalento).getIdentificador();
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
     * Verifica se a categoria tem atributos relacionados com formação de descrição marcada
     * 
     * @param objCategoriaTalento Categoria de talento a ser verificada
     * 
     * @return boolean Com a verificação
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
     * Verifica se a categoria tem atributos relacionados com formação de descrição marcada
     * 
     * @param objCategoriaTalento Categoria de talento a ser verificada
     * 
     * @return boolean Com a verificação
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
     *  Método que pede a inicialização da propriedade gruposCategoriaTalento, que é lazy
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
     *  Método que pede a inicialização da propriedade gruposCategoriaTalento, que é lazy
     * 
     * @param objCategoriaTalento Lista com os objetos que têm o atributo que vai ser recuperado
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
