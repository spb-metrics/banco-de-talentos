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
import br.gov.camara.negocio.bancotalentos.pojo.AtributoTalento;
import br.gov.camara.negocio.bancotalentos.pojo.CategoriaAtributoTalento;
import br.gov.camara.negocio.bancotalentos.pojo.CategoriaTalento;
import br.gov.camara.negocio.exception.DAOException;
import br.gov.camara.negocio.util.Consulta;
import br.gov.camara.negocio.util.ConsultaComum;

/**
 * Classe DAO para a tabeça CategoriaAtributoTalento
 */

public class CategoriaAtributoTalentoDAO extends DAO implements ConsultaComum
{
    // Inicializa o log
    private static Log log = LogFactory.getLog(CategoriaAtributoTalentoDAO.class);

    /**
     * Construtor sem argumentos
     *
     */
    public CategoriaAtributoTalentoDAO()
    {
        super("Categoria/atributos de talento");
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
        String strConsulta = " FROM" + " CategoriaAtributoTalento categoriaAtributoTalento" +

        " ORDER BY" + " categoriaAtributoTalento.sequencialPreenchimento ASC";

        return obter(strConsulta);

    }

    /**
     * Obtém todos os registros relacionados a determinada categoria de talento
     * 
     * @param objCategoriaTalento Categoria de talento desejada
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public List obterPorCategoriaTalento(CategoriaTalento objCategoriaTalento) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        String strConsulta = " FROM" + " CategoriaAtributoTalento categoriaAtributoTalento" +

        " WHERE" + " categoriaAtributoTalento.categoriaTalento.identificador = " + objCategoriaTalento.getIdentificador() +

        " ORDER BY" + " categoriaAtributoTalento.sequencialOrdenacao ASC";

        return obter(strConsulta);
    }

    /**
     * Obtém todos os registros para consulta relacionados a determinada categoria de talento
     * 
     * @param objCategoriaTalento Categoria de talento desejada
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public List obterParaConsultaPorCategoriaTalento(CategoriaTalento objCategoriaTalento) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        String strConsulta = " SELECT DISTINCT " + " categoriaAtributoTalento " +

        " FROM" + " CategoriaAtributoTalento categoriaAtributoTalento" +

        " WHERE" + " categoriaAtributoTalento.categoriaTalento.identificador = " + objCategoriaTalento.getIdentificador()
        //                + " AND (categoriaAtributoTalento.atributoTalento.tipoHTML.multiplicidade IN('U','M') "
                //                + " OR categoriaAtributoTalento.atributoTalento.tipoDado IN('D','N')) "
                +

                " ORDER BY"
                + " categoriaAtributoTalento.sequencialOrdenacao ASC";

        return obter(strConsulta);
    }

    /**
     * Obtém todos os registros filhos de determinada categoria/atributo de talento
     * 
     * @param objCategoriaAtributoTalento Categoria/atributo de talento desejado
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public List obterTodosOsFilhos(CategoriaAtributoTalento objCategoriaAtributoTalento) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        String strConsulta = " FROM"
                + " CategoriaAtributoTalento categoriaAtributoTalento"
                +

                " WHERE"
                + " categoriaAtributoTalento.categoriaTalento.identificador = "
                + objCategoriaAtributoTalento.getCategoriaTalento().getIdentificador()
                + " AND categoriaAtributoTalento.atributoTalento IN "
                + " (FROM AtributoTalento atributoTalento"
                + " WHERE atributoTalento.atributoTalentoPai.identificador = "
                + objCategoriaAtributoTalento.getAtributoTalento().getIdentificador()
                + ")";

        // Retorna consulta
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

        String strConsulta = " FROM" + " CategoriaAtributoTalento categoriaAtributoTalento" +

        " WHERE " + "categoriaAtributoTalento.identificador = " + strChave;

        // Consulta
        List lstResultado = obter(strConsulta);
        if (!lstResultado.isEmpty())
        {
            return lstResultado.get(0);
        }
        else
        {
            return null;
        }
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

        " FROM" + " AtributoTalentoOpcao atributoTalentoOpcao";

        // Recupera os dados
        return obterTotalRegistros(strConsulta);
    }

    /**
     * Obtém o total de registros relacionados a determinada categoria de talento
     *
     * @param objCategoriaTalento Categoria de talento desejada
     *
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public int obterTotalRegistrosPorCategoriaTalento(CategoriaTalento objCategoriaTalento) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        String strConsulta = " SELECT" + " COUNT(*)" +

        " FROM" + " CategoriaAtributoTalento categoriaAtributoTalento" +

        " WHERE" + " categoriaAtributoTalento.categoriaTalento.identificador = " + objCategoriaTalento.getIdentificador();

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
        String strConsulta = " FROM" + " CategoriaAtributoTalento categoriaAtributoTalento" +

        " ORDER BY" + " categoriaAtributoTalento.atributoTalento.nome ASC";

        // Recupera os dados
        return obterPorPagina(intNumeroPagina, intMaximoPagina, strConsulta);
    }

    /**
     * Obtém os registros de determinada página relacionados a determinada categoria de talento
     *
     * @param objCategoriaTalento Categoria de talento desejada
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public List obterPorPaginaPorCategoriaTalento(CategoriaTalento objCategoriaTalento, int intNumeroPagina, int intMaximoPagina) throws DAOException
    {
        // Monta consulta
        String strConsulta = " FROM" + " CategoriaAtributoTalento categoriaAtributoTalento" +

        " WHERE" + " categoriaAtributoTalento.categoriaTalento.identificador = " + objCategoriaTalento.getIdentificador() +

        " ORDER BY" + " categoriaAtributoTalento.sequencialOrdenacao ASC";

        // Recupera os dados
        return obterPorPagina(intNumeroPagina, intMaximoPagina, strConsulta);
    }

    /**
     * Obtém o último sequencial de ordenação para a categoria passada
     *
     * @param objCategoriaTalento Categoria de talento a se verificar o último sequencial
     *
     * @return int contendo o valor requerido
     * 
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public int obterUltimoSequencialOrdenacao(CategoriaTalento objCategoriaTalento) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        //Declarações
        int intRetorno = 0;

        // Monta query
        String strConsulta = " SELECT " + " MAX(categoriaAtributoTalento.sequencialOrdenacao)" +

        " FROM" + " CategoriaAtributoTalento categoriaAtributoTalento" +

        " WHERE" + " categoriaAtributoTalento.categoriaTalento.identificador = " + objCategoriaTalento.getIdentificador();

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
    public String excluirImpl(Object objCategoriaAtributoTalento) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Retorna consulta de exclusão
        return " FROM" + " CategoriaAtributoTalento categoriaAtributoTalento" +

        " WHERE" + " categoriaAtributoTalento.identificador = " + ((CategoriaAtributoTalento) objCategoriaAtributoTalento).getIdentificador();
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
            String strConsulta = " SELECT COUNT(*)" + " FROM" + " CategoriaAtributoTalento categoriaAtributoTalento";
            if (!strFiltro.trim().equals(""))
            {
                strConsulta += " WHERE " + strFiltro;
            }

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

    /**
     * Método utilizado para efetuar consultas genéricas, por página
     * 
     * @param objConsulta Objeto de consulta contendo os parâmetros necessários para montar a query
     * @param intNumeroPagina Número da página a ser exibida
     * @param intMaximoPagina Número total de registros da página
     * 
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
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
            String strConsulta = " FROM" + " CategoriaAtributoTalento categoriaAtributoTalento";
            if (!strFiltro.trim().equals(""))
            {
                strConsulta += " WHERE " + strFiltro;
            }
            strConsulta += " ORDER BY" + " categoriaAtributoTalento.seqPreenchimento ASC";

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
     * Verifica a existência da relação categoria/atributo 
     * 
     * @param objCategoriaTalento Categoria de talento a ser verificada
     * @param objAtributoTalento Atributo de talento a ser verificado
     * 
     * @return boolean Com a verificação
     * 
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public boolean verificarExistenciaAtributoRelacionado(CategoriaTalento objCategoriaTalento, AtributoTalento objAtributoTalento) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        String strConsulta = " SELECT "
                + "COUNT(*)"
                +

                " FROM"
                + " CategoriaAtributoTalento categoriaAtributoTalento"
                +

                " WHERE"
                + " categoriaAtributoTalento.categoriaTalento.identificador = "
                + objCategoriaTalento.getIdentificador()
                + " AND categoriaAtributoTalento.atributoTalento.identificador = "
                + objAtributoTalento.getIdentificador();

        // Recupera os dados
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
     * Verifica a existência de atributos talento para talento
     * 
     * @param objCategoriaAtributoTalento Categoria/atributo de talento a ser verificada
     * 
     * @return int total de talentos associados
     * 
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public int verificarExistenciaCategoriaAtributosTalentoTalento(CategoriaAtributoTalento objCategoriaAtributoTalento) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        String strConsulta = " SELECT " + "COUNT(*)" +

        " FROM" + " Talento talento" +

        " WHERE" + " talento.categoriaTalento.identificador = " + objCategoriaAtributoTalento.getCategoriaTalento().getIdentificador();

        // Recupera os dados
        int intTotalRegistros = obterTotalRegistros(strConsulta);

        return intTotalRegistros;
    }

    /**
     * Verifica a existência de atributos filho para a categoria/atributo desejada  
     * 
     * @param objCategoriaAtributoTalento Categoria/atributo de talento a ser verificada
     * 
     * @return boolean Com a verificação
     * 
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public boolean verificarExistenciaCategoriaAtributosTalentoFilhos(CategoriaAtributoTalento objCategoriaAtributoTalento) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        String strConsulta = " SELECT "
                + "COUNT(*)"
                +

                " FROM"
                + " CategoriaAtributoTalento categoriaAtributoTalento"
                +

                " WHERE"
                + " categoriaAtributoTalento.categoriaTalento.identificador = "
                + objCategoriaAtributoTalento.getCategoriaTalento().getIdentificador()
                + " AND categoriaAtributoTalento.atributoTalento.identificador IN "
                + " (SELECT atributoTalento.identificador"
                + " FROM AtributoTalento atributoTalento"
                + " WHERE atributoTalento.atributoTalentoPai.identificador = "
                + objCategoriaAtributoTalento.getAtributoTalento().getIdentificador()
                + ")";

        // Recupera os dados
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
     * Obtém categoria/atributo de acordo com a categoria e o atributo passados
     * 
     * @param objCategoriaTalento Categoria de talento a ser consultada
     * @param objAtributoTalento Atributo de talento a ser consultado
     * 
     * @return CategoriaAtributoTalento Contendo a categoria/atributo
     * 
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public CategoriaAtributoTalento obterPorCategoriaTalentoAtributoTalento(CategoriaTalento objCategoriaTalento, AtributoTalento objAtributoTalento) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        String strConsulta = " FROM"
                + " CategoriaAtributoTalento categoriaAtributoTalento"
                +

                " WHERE"
                + " categoriaAtributoTalento.categoriaTalento.identificador = "
                + objCategoriaTalento.getIdentificador()
                + " AND categoriaAtributoTalento.atributoTalento.identificador = "
                + objAtributoTalento.getIdentificador();

        // Recupera os dados
        List lstResultado = obter(strConsulta);
        if (!lstResultado.isEmpty())
        {
            return (CategoriaAtributoTalento) lstResultado.get(0);
        }
        else
        {
            return null;
        }
    }

    /**
     *  Método que pede a inicialização da propriedade categoriaTalento, que é lazy
     * 
     * @param objCategoriaAtributoTalento Objeto que tem o atributo que vai ser recuperado
     * 
     * @see br.gov.camara.negocio.DAO#inicializarRelacionamento(java.lang.Object)
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public void inicializarCategoriaTalento(CategoriaAtributoTalento objCategoriaAtributoTalento) throws DAOException
    {
        inicializarRelacionamento(objCategoriaAtributoTalento.getCategoriaTalento());
    }

    /**
     *  Método que pede a inicialização da propriedade categoriaTalento, que é lazy
     * 
     * @param List lstCategoriaAtributosTalento Lista com os objetos que têm o atributo que vai ser recuperado
     * 
     * @see br.gov.camara.negocio.DAO#inicializarRelacionamento(java.lang.Object)
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public void inicializarCategoriaTalento(List lstCategoriaAtributosTalento) throws DAOException
    {
        Iterator itrCategoriaAtributosTalento = lstCategoriaAtributosTalento.iterator();
        while (itrCategoriaAtributosTalento.hasNext())
        {
            inicializarCategoriaTalento((CategoriaAtributoTalento) itrCategoriaAtributosTalento.next());
        }
    }

    /**
     *  Método que pede a inicialização da propriedade atributoTalento, que é lazy
     * 
     * @param  objCategoriaAtributoTalento Objeto que tem o atributo que vai ser recuperado
     * 
     * @see br.gov.camara.negocio.DAO#inicializarRelacionamento(java.lang.Object)
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public void inicializarAtributoTalento(CategoriaAtributoTalento objCategoriaAtributoTalento) throws DAOException
    {
        inicializarRelacionamento(objCategoriaAtributoTalento.getAtributoTalento());
        inicializarRelacionamento(objCategoriaAtributoTalento.getAtributoTalento().getTipoHTML());
    }

    /**
     *  Método que pede a inicialização da propriedade atributoTalento, que é lazy
     * 
     * @param List lstCategoriaAtributosTalento Lista com os objetos que tem o atributo que vai ser recuperado
     * 
     * @see br.gov.camara.negocio.DAO#inicializarRelacionamento(java.lang.Object)
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public void inicializarAtributoTalento(List lstCategoriaAtributosTalento) throws DAOException
    {
        Iterator itrCategoriaAtributosTalento = lstCategoriaAtributosTalento.iterator();
        while (itrCategoriaAtributosTalento.hasNext())
        {
            inicializarAtributoTalento((CategoriaAtributoTalento) itrCategoriaAtributosTalento.next());
        }
    }
}
