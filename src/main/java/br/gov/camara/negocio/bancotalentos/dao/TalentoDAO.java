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
import br.gov.camara.negocio.bancotalentos.pojo.CategoriaAtributoTalento;
import br.gov.camara.negocio.bancotalentos.pojo.CategoriaTalento;
import br.gov.camara.negocio.bancotalentos.pojo.Pessoa;
import br.gov.camara.negocio.bancotalentos.pojo.Talento;
import br.gov.camara.negocio.bancotalentos.util.BancoTalentosUtil;
import br.gov.camara.negocio.exception.DAOException;
import br.gov.camara.negocio.util.Consulta;
import br.gov.camara.negocio.util.ConsultaComum;

/**
 * Classe DAO para a tabela Talento
 */
public class TalentoDAO extends DAO implements ConsultaComum
{
    private static Log log = LogFactory.getLog(TalentoDAO.class);

    /**
     * Construtor sem argumentos
     */
    public TalentoDAO()
    {
        super("Talentos");
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
        String strConsulta = " FROM" + " Talento talento" +

        " ORDER BY" + " talento.identificador ASC";

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

        // Monta consulta
        String strConsulta = " FROM Talento " + "talento" +

        " WHERE " + "talento.identificador = " + strChave;

        // Efetua consulta 
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
     * Obtém o total de registros por pessoa
     * 
     * @param objPessoa Pessoa desejada
     * 
     * @return int Contendo o total
     *
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public int obterTotalRegistrosPorPessoa(Pessoa objPessoa) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        String strConsulta = " SELECT " + "COUNT(*)" +

        " FROM" + " Talento talento" +

        " WHERE" + " talento.pessoa.identificador = " + objPessoa.getIdentificador();

        // Recupera os dados
        return obterTotalRegistros(strConsulta);
    }

    /**
     * Obtém total de pessoas com talentos cadastrados
     * 
     * @return int Contendo o total
     *
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public int obterTotalPessoasComTalentos() throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        String strConsulta = " SELECT " + " COUNT(DISTINCT talento.pessoa.identificador)" +

        " FROM" + " Talento talento";

        // Recupera os dados
        return obterTotalRegistros(strConsulta);
    }

    /**
     * Obtém o total de registros por pessoa e categoria de talento
     * 
     * @param objPessoa Pessoa desejada
     * @param objCategoriaTalento Categoria de talento desejada
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public int obterTotalRegistrosPorPessoaCategoriaTalento(Pessoa objPessoa, CategoriaTalento objCategoriaTalento) throws DAOException
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
                + " Talento talento"
                +

                " WHERE"
                + " talento.pessoa.identificador = "
                + objPessoa.getIdentificador()
                + " AND talento.categoriaTalento.identificador = "
                + objCategoriaTalento.getIdentificador();

        // Recupera os dados
        return obterTotalRegistros(strConsulta);
    }

    /**
     * Obtém os registros da pessoa desejada de determinada página
     * 
     * @param objPessoa Pessoa desejada
     * @param int Número da página a ser mostrada
     * @param int Quantidade de registros
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public List obterPorPessoaPorPagina(Pessoa objPessoa, int intNumeroPagina, int intMaximoPagina) throws DAOException
    {
        // Monta consulta
        String strConsulta = " FROM" + " Talento talento" +

        " WHERE" + " talento.pessoa.identificador = " + objPessoa.getIdentificador() +

        " ORDER BY" + " talento.categoriaTalento.sequencialOrdenacao ASC," + " talento.dataLancamento DESC";

        // Recupera os dados
        return obterPorPagina(intNumeroPagina, intMaximoPagina, strConsulta);
    }

    /**
     * Obtém os registros da pessoa desejada 
     * 
     * @param objPessoa Pessoa desejada
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public List obterPorPessoa(Pessoa objPessoa) throws DAOException
    {
        // Monta consulta
        String strConsulta = " FROM" + " Talento talento" +

        " WHERE" + " talento.pessoa.identificador = " + objPessoa.getIdentificador() +

        " ORDER BY" + " talento.categoriaTalento.sequencialOrdenacao";

        // Recupera os dados
        return obter(strConsulta);
    }

    /**
     * Obtém os registros da pessoa desejada e pela categoria escolhida, de determinada página
     * 
     * @param objPessoa Pessoa desejada
     * @param objCategoriaTalento Categoria de talento desejada
     * @param int Número da página a ser mostrada
     * @param int Quantidade de registros
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public List obterPorPessoaCategoriaTalentoPorPagina(Pessoa objPessoa, CategoriaTalento objCategoriaTalento, int intNumeroPagina, int intMaximoPagina) throws DAOException
    {
        // Monta consulta
        String strConsulta = " FROM"
                + " Talento talento"
                +

                " WHERE"
                + " talento.pessoa.identificador = "
                + objPessoa.getIdentificador()
                + " AND talento.categoriaTalento.identificador = "
                + objCategoriaTalento.getIdentificador()
                +

                " ORDER BY"
                + " talento.categoriaTalento.sequencialOrdenacao ASC,"
                + " talento.dataLancamento DESC";

        // Recupera os dados
        return obterPorPagina(intNumeroPagina, intMaximoPagina, strConsulta);
    }

    /**
     * Verifica se a pessoa já tem um talento cadastrado para a categoria especificada
     * 
     * @param objPessoa Pessoa desejada
     * @param objCategoriaTalento Categoria de talento desejada
     * 
     * @return List Contendo a verificação
     *
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public boolean verificarExistenciaPorPessoaCategoriaTalento(Pessoa objPessoa, CategoriaTalento objCategoriaTalento) throws DAOException
    {
        // Monta consulta
        String strConsulta = " SELECT"
                + " COUNT(*)"
                +

                " FROM"
                + " Talento talento"
                +

                " WHERE"
                + " talento.pessoa.identificador = "
                + objPessoa.getIdentificador()
                + " AND talento.categoriaTalento.identificador = "
                + objCategoriaTalento.getIdentificador();

        // Conta registros
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
     * Exclui um registro
     *
     * @param Object POJO representando o objeto a ser excluído
     * 
     * @return String Contendo a consulta de exclusão 
     * 
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public String excluirImpl(Object objTalento) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Retorna consulta de exclusão
        return " FROM" + " Talento talento" +

        " WHERE" + " talento.identificador = " + ((Talento) objTalento).getIdentificador();
    }

    /**
     * Exclui registros pela sua categoria/atributo
     *
     * @param CategoriaAtributoTalento Com a categoria/atributo desejado
     * 
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public void excluirPorCategoriaTalento(CategoriaTalento objCategoriaTalento) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta consulta de exclusão
        String strConsulta = " FROM" + " Talento talento" +

        " WHERE" + " talento.categoriaTalento.identificador = " + objCategoriaTalento.getIdentificador();

        //Executa
        excluir(strConsulta);
    }

    /**
     * Exclui registros pela sua categoria/atributo
     *
     * @param CategoriaAtributoTalento Com a categoria/atributo desejado
     * 
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public void excluirPorCategoriaAtributoTalento(CategoriaAtributoTalento objCategoriaAtributoTalento) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta consulta de exclusão
        String strConsulta = " FROM" + " Talento talento" +

        " WHERE" + " talento.categoriaAtributoTalento.identificador = " + objCategoriaAtributoTalento.getIdentificador();

        //Executa
        excluir(strConsulta);
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
            String strConsulta = " SELECT COUNT(*)" + " FROM" + " Talento talento";
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
     * Obtém AtributoTalento de acordo com filtro passado
     *
     * @param int Número da página a ser mostrada
     * @param int Quantidade de registros
     * @param Consulta objeto contendo os filtros para consulta
     *  
     * @return List contendo o resultado da pesquisa
     * 
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
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
            String strConsulta = " FROM" + " Talento talento";
            if (!strFiltro.trim().equals(""))
            {
                strConsulta += " WHERE " + strFiltro;
            }
            strConsulta += " ORDER BY" + " talento.categoria.ordenacao ASC";

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
     *  Método que pede a inicialização da propriedade categoriaTalento, que é lazy
     * 
     * @param  objTalento Objeto que tem o atributo que vai ser recuperado
     * 
     * @see br.gov.camara.negocio.DAO#inicializarRelacionamento(java.lang.Object)
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public void inicializarCategoriaTalento(Talento objTalento) throws DAOException
    {
        inicializarRelacionamento(objTalento.getCategoriaTalento());
    }

    /**
     *  Método que pede a inicialização da propriedade atributoTalentoOpcao, que é lazy
     * 
     * @param lstTalentos Lista com os objetos que têm o atributo que vai ser recuperado
     * 
     * @see br.gov.camara.negocio.DAO#inicializarRelacionamento(java.lang.Object)
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public void inicializarCategoriaTalento(List lstTalentos) throws DAOException
    {
        Iterator itrTalentos = lstTalentos.iterator();
        while (itrTalentos.hasNext())
        {
            inicializarCategoriaTalento((Talento) itrTalentos.next());
        }
    }

    /**
     *  Método que pede a inicialização da propriedade pessoa, que é lazy
     * 
     * @param  objTalento Objeto que tem o atributo que vai ser recuperado
     * 
     * @see br.gov.camara.negocio.DAO#inicializarRelacionamento(java.lang.Object)
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public void inicializarPessoa(Talento objTalento) throws DAOException
    {
        inicializarRelacionamento(objTalento.getPessoa());
    }

    /**
     *  Método que pede a inicialização da propriedade pessoa, que é lazy
     * 
     * @param  objTalento Objeto que tem o atributo que vai ser recuperado
     * 
     * @see br.gov.camara.negocio.DAO#inicializarRelacionamento(java.lang.Object)
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public void inicializarGrupo(Talento objTalento) throws DAOException
    {
        inicializarRelacionamento(objTalento.getPessoa().getGrupo());
    }

    /**
     *  Método que pede a inicialização da propriedade pessoa, que é lazy
     * 
     * @param lstTalentos Lista com os objetos que têm o atributo que vai ser recuperado
     * 
     * @see br.gov.camara.negocio.DAO#inicializarRelacionamento(java.lang.Object)
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public void inicializarPessoa(List lstTalentos) throws DAOException
    {
        Iterator itrTalentos = lstTalentos.iterator();
        while (itrTalentos.hasNext())
        {
            Talento talento = (Talento) itrTalentos.next();
            inicializarPessoa(talento);
            inicializarGrupo(talento);
        }
    }

    /**
     * Obtém o total de registros por intervalo de data / categoria / grupo
     * A formatação de data é realizada através do mecanismo de substituição do Hibernate. 
     * As funções específicas do banco devem ser especificadas na chave "hibernate.query.substitutions" 
     * que se encontram no arquivo de configuração do Hibernate "hibernate-config.xml".
     * 
     * @param objPessoa Pessoa desejada
     * @param objCategoriaTalento Categoria de talento desejada
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public List realizarConsultaEstatistica(String strDataInicial, String strDataTermino, List lstOrdenacao, String strGruposHabilitados) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        String strOrdenacao = "";
        if (lstOrdenacao != null)
        {
            for (int i = 0; i < lstOrdenacao.size(); i++)
            {
                String strQuebra = (String) lstOrdenacao.get(i);
                if (strQuebra.equalsIgnoreCase("grupo"))
                {
                    strOrdenacao += " talento.pessoa.grupo.codigo" + ",";
                }
                else if (strQuebra.equalsIgnoreCase("categoria"))
                {
                    strOrdenacao += " talento.categoriaTalento.nome" + ",";
                }
                else if (strQuebra.equalsIgnoreCase("data"))
                {
                    strOrdenacao += " talento.dataLancamento" + ",";
                }
            }
            strOrdenacao += " talento.pessoa.nome" + ",";

            if (!"".equals(strOrdenacao))
            {
                strOrdenacao = strOrdenacao.substring(1, strOrdenacao.length() - 1);
            }
        }
        // Monta query
        String strConsulta = " FROM " + " Talento talento ";

        String strLigacao = " WHERE ";

        if (strDataInicial != null && !"".equals(strDataInicial))
        {
            strConsulta += strLigacao
                    + BancoTalentosUtil.utilizarFuncaoBDRemocaoHorasDeData(this, "talento.dataLancamento")
                    + ">="
                    + BancoTalentosUtil.utilizarFuncaoBDConversaoStringEmData(this, "'" + strDataInicial + "'");
            strLigacao = " AND ";
        }
        if (strDataTermino != null && !"".equals(strDataTermino))
        {
            strConsulta += strLigacao
                    + BancoTalentosUtil.utilizarFuncaoBDRemocaoHorasDeData(this, "talento.dataLancamento")
                    + "<="
                    + BancoTalentosUtil.utilizarFuncaoBDConversaoStringEmData(this, "'" + strDataTermino + "'");
            strLigacao = " AND ";
        }
        if (strGruposHabilitados != null && !"".equals(strGruposHabilitados))
        {
            strConsulta += strLigacao + "talento.pessoa.grupo.codigo in (" + strGruposHabilitados + ")";
            strLigacao = " AND ";
        }
        if (strOrdenacao != null && !"".equals(strOrdenacao))
        {
            strConsulta += " ORDER BY " + strOrdenacao;
        }
        else
        {
            //strConsulta += " GROUP BY talento.pessoa.identificador";
            strConsulta += " ORDER BY talento.pessoa.identificador";
        }

        // Recupera os dados
        return obter(strConsulta);

    }

    /**
     * Obtém o total de registros por intervalo de data / categoria / grupo
     * A formatação de data é realizada através do mecanismo de substituição do Hibernate. 
     * As funções específicas do banco devem ser especificadas na chave "hibernate.query.substitutions" 
     * que se encontram no arquivo de configuração do Hibernate "hibernate-config.xml".
     * 
     * @param objPessoa Pessoa desejada
     * @param objCategoriaTalento Categoria de talento desejada
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public List obterListagemConsulta(String strDataInicial, String strDataTermino, String strGrupo, String strCategoria, String strData, String strGruposHabilitados) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        String strConsulta = " FROM" + " Talento talento ";

        String strLigacao = " WHERE ";

        if (strDataInicial != null && !"".equals(strDataInicial))
        {
            strConsulta += strLigacao
                    + BancoTalentosUtil.utilizarFuncaoBDRemocaoHorasDeData(this, "talento.dataLancamento")
                    + ">="
                    + BancoTalentosUtil.utilizarFuncaoBDConversaoStringEmData(this, "'" + strDataInicial + "'");
            strLigacao = " AND ";
        }
        if (strDataTermino != null && !"".equals(strDataTermino))
        {
            strConsulta += strLigacao
                    + BancoTalentosUtil.utilizarFuncaoBDRemocaoHorasDeData(this, "talento.dataLancamento")
                    + "<="
                    + BancoTalentosUtil.utilizarFuncaoBDConversaoStringEmData(this, "'" + strDataTermino + "'");
            strLigacao = " AND ";
        }
        if (strGrupo != null && !"".equals(strGrupo))
        {
            strConsulta += strLigacao + " talento.pessoa.grupo.codigo = " + strGrupo;
            strLigacao = " AND ";
        }
        if (strCategoria != null && !"".equals(strCategoria))
        {
            strConsulta += strLigacao + " talento.categoriaTalento.identificador = " + strCategoria;
            strLigacao = " AND ";
        }
        if (strData != null && !"".equals(strData))
        {
            strConsulta += strLigacao
                    + BancoTalentosUtil.utilizarFuncaoBDRemocaoHorasDeData(this, "talento.dataLancamento")
                    + "="
                    + BancoTalentosUtil.utilizarFuncaoBDConversaoStringEmData(this, "'" + strData + "'");
            strLigacao = " AND ";
        }
        if (strGruposHabilitados != null && !"".equals(strGruposHabilitados))
        {
            strConsulta += strLigacao + "talento.pessoa.grupo.codigo in (" + strGruposHabilitados + ")";
            strLigacao = " AND ";
        }

        strConsulta += " ORDER BY talento.pessoa.nome";

        // Recupera os dados
        return obter(strConsulta);

    }

    /**
     * Obtém o total de registros por intervalo de data / categoria / grupo. 
     * A formatação de data é realizada através do mecanismo de substituição do Hibernate. 
     * As funções específicas do banco devem ser especificadas na chave "hibernate.query.substitutions" 
     * que se encontram no arquivo de configuração do Hibernate "hibernate-config.xml".
     * 
     * @param objPessoa Pessoa desejada
     * @param objCategoriaTalento Categoria de talento desejada
     *  
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public int obterTotalListagemConsulta(String strDataInicial, String strDataTermino, String strGrupo, String strCategoria, String strData, String strGruposHabilitados) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        String strConsulta = " SELECT COUNT(DISTINCT talento.pessoa.identificador)" + " FROM" + " Talento talento ";

        String strLigacao = " WHERE ";

        if (strDataInicial != null && !"".equals(strDataInicial))
        {
            strConsulta += strLigacao
                    + BancoTalentosUtil.utilizarFuncaoBDRemocaoHorasDeData(this, "talento.dataLancamento")
                    + ">="
                    + BancoTalentosUtil.utilizarFuncaoBDConversaoStringEmData(this, "'" + strDataInicial + "'");
            strLigacao = " AND ";
        }
        if (strDataTermino != null && !"".equals(strDataTermino))
        {
            strConsulta += strLigacao
                    + BancoTalentosUtil.utilizarFuncaoBDRemocaoHorasDeData(this, "talento.dataLancamento")
                    + "<="
                    + BancoTalentosUtil.utilizarFuncaoBDConversaoStringEmData(this, "'" + strDataTermino + "'");
            strLigacao = " AND ";
        }
        if (strGrupo != null && !"".equals(strGrupo))
        {
            strConsulta += strLigacao + " talento.pessoa.grupo.codigo = " + strGrupo;
            strLigacao = " AND ";
        }
        if (strCategoria != null && !"".equals(strCategoria))
        {
            strConsulta += strLigacao + " talento.categoriaTalento.identificador = " + strCategoria;
            strLigacao = " AND ";
        }
        if (strData != null && !"".equals(strData))
        {
            strConsulta += strLigacao
                    + BancoTalentosUtil.utilizarFuncaoBDRemocaoHorasDeData(this, "talento.dataLancamento")
                    + "="
                    + BancoTalentosUtil.utilizarFuncaoBDConversaoStringEmData(this, "'" + strData + "'");
            strLigacao = " AND ";
        }
        if (strGruposHabilitados != null && !"".equals(strGruposHabilitados))
        {
            strConsulta += strLigacao + "talento.pessoa.grupo.codigo in (" + strGruposHabilitados + ")";
            strLigacao = " AND ";
        }

        // Recupera os dados
        return obterTotalRegistros(strConsulta);

    }

}
