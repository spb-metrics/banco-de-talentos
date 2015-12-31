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

package br.gov.camara.negocio.autenticacaoperfil.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.gov.camara.biblioteca.util.ClasseDinamica;
import br.gov.camara.exception.CDException;
import br.gov.camara.negocio.DAO;
import br.gov.camara.negocio.autenticacaoperfil.pojo.CriterioAcessoPerfil;
import br.gov.camara.negocio.autenticacaoperfil.pojo.PerfilSistema;
import br.gov.camara.negocio.autenticacaoperfil.pojo.UsuarioCriterioAcessoPerfil;
import br.gov.camara.negocio.autenticacaoperfil.pojo.UsuarioSistema;
import br.gov.camara.negocio.exception.DAOException;
import br.gov.camara.negocio.util.Consulta;
import br.gov.camara.negocio.util.ConsultaComum;

/**
 * 
 * Classe DAO para perfil de sistema
 *
 */

public class UsuarioSistemaDAO extends DAO implements ConsultaComum
{
    // Inicializa o log
    private static Log log = LogFactory.getLog(DAO.class);

    private static final int IDENTIFICADOR_INICIAL = 5000000;

    /**
     * Construtor sem argumentos
     *
     */
    public UsuarioSistemaDAO()
    {
        super("Usuário de sistema");
    }

    /**
     * Construtor sem argumentos
     *
     */
    public UsuarioSistemaDAO(String nomeConexao)
    {
        super("Usuário de sistema", nomeConexao);
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
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" UsuarioSistema usuarioSistema");
        strbConsulta.append(" ORDER BY");
        strbConsulta.append(" usuarioSistema.nome ASC");

        // Retorna
        return this.obter(strbConsulta.toString());
    }

    /**
     * Obtém todos os registros por página
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public List obterTodosPorPagina(int intNumeroPagina, int intMaximoPagina) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        List lstRetorno = null;

        try
        {
            // Monta query
            StringBuffer strbConsulta = new StringBuffer();

            strbConsulta.append(" FROM");
            strbConsulta.append(" UsuarioSistema usuarioSistema");
            strbConsulta.append(" ORDER BY");
            strbConsulta.append(" usuarioSistema.nome ASC");

            // Recupera os dados 
            lstRetorno = this.obterPorPagina(intNumeroPagina, intMaximoPagina, strbConsulta.toString());
            Iterator itrRetorno = lstRetorno.iterator();
            lstRetorno = new ArrayList();
            while (itrRetorno.hasNext())
            {
                UsuarioSistema objUsuarioSistema = (UsuarioSistema) itrRetorno.next();
                objUsuarioSistema.setChaveConsulta(objUsuarioSistema.getIdentificador() + ";" + objUsuarioSistema.getNome());
                lstRetorno.add(objUsuarioSistema);
            }
        }
        catch (CDException cde)
        {
            throw new DAOException("Ocorreu um erro ao consultar registros na classe " + this.strNomeClasse, cde);
        }
        return lstRetorno;
    }

    /**
     * Obtém total de registros
     * 
     * @return int Contendo o total de registro da consulta
     *
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public String incluir(Object objeto) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        UsuarioSistema usuarioSistema = (UsuarioSistema) objeto;
        if (usuarioSistema.getIdentificador() == null)
        {
            String consulta = "SELECT MAX(usuarioSistema.identificador) FROM UsuarioSistema usuarioSistema";

            int identificador = 0;
            List retorno = this.obter(consulta);
            if (!retorno.isEmpty())
            {
                identificador = ((Integer) retorno.get(0)).intValue();
            }

            if (identificador < IDENTIFICADOR_INICIAL - 1)
            {
                identificador = IDENTIFICADOR_INICIAL;
            }
            else
            {
                identificador++;
            }

            usuarioSistema.setIdentificador(new Integer(identificador));
        }

        // Retorna
        return super.incluir(objeto);
    }

    /**
     * Obtém total de registros
     * 
     * @return int Contendo o total de registro da consulta
     *
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public int obterTotalRegistros() throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" SELECT COUNT(*)");
        strbConsulta.append(" FROM");
        strbConsulta.append(" UsuarioSistema usuarioSistema");

        // Retorna
        return this.obterTotalRegistros(strbConsulta.toString());
    }

    /**
     * Obtém total de registro pelo nome
     * 
     * @return int Contento o total de registro da consulta
     *
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public int obterTotalRegistrosPeloNome(String strNome) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" SELECT COUNT(*)");
        strbConsulta.append(" FROM");
        strbConsulta.append(" UsuarioSistema usuarioSistema");
        strbConsulta.append(" WHERE UPPER(usuarioSistema.nome) like '%");
        strbConsulta.append(strNome.toUpperCase());
        strbConsulta.append("%'");

        // Retorna
        return this.obterTotalRegistros(strbConsulta.toString());
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

        Object objRetorno = null;

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" UsuarioSistema usuarioSistema");
        strbConsulta.append(" WHERE");
        strbConsulta.append(" usuarioSistema.identificador = ");
        strbConsulta.append(strChave);

        // Verifica se há retorno
        List lstResultado = this.obter(strbConsulta.toString());
        if (!lstResultado.isEmpty())
        {
            UsuarioSistema objUsuarioSistema = (UsuarioSistema) lstResultado.get(0);
            objUsuarioSistema.setChaveConsulta(objUsuarioSistema.getIdentificador() + ";" + objUsuarioSistema.getNome());
            objRetorno = objUsuarioSistema;
        }
        return objRetorno;
    }

    /**
     * Obtém um registro a partir do login
     *
     * @param strLogin Chave do registro a ser obtido
     * 
     * @return Object POJO representando o registro obtido
     *
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public UsuarioSistema obterPeloLogin(String strLogin) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        UsuarioSistema objRetorno = null;

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" UsuarioSistema usuarioSistema");
        strbConsulta.append(" WHERE");
        strbConsulta.append(" UPPER(usuarioSistema.login) = '");
        strbConsulta.append(strLogin.toUpperCase());
        strbConsulta.append("'");

        // Verifica se há retorno
        List lstResultado = this.obter(strbConsulta.toString());

        if (lstResultado != null && !lstResultado.isEmpty())
        {
            if (lstResultado.size() == 1)
            {
                objRetorno = (UsuarioSistema) lstResultado.get(0);
            }
            else
            {
                throw new DAOException("Foi encontrado mais de um servidor com esse login ('" + strLogin + "')");
            }
        }
        return objRetorno;
    }

    /**
     * Obtém lista de registros pelo nome por página
     *
     * @param String Nome a ser consultado
     * @param int Número da página a ser consultada
     * @param int Máximo de registro por página
     * 
     * @return Object POJO representando o registro obtido
     *
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public List obterPeloNomePorPagina(String strNome, int intNumeroPagina, int intMaximoPagina) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        List lstRetorno = null;

        try
        {
            // Monta query
            StringBuffer strbConsulta = new StringBuffer();

            strbConsulta.append(" FROM");
            strbConsulta.append(" UsuarioSistema usuarioSistema");
            strbConsulta.append(" WHERE");
            strbConsulta.append(" UPPER(usuarioSistema.nome) like '%");
            strbConsulta.append(strNome.toUpperCase().trim());
            strbConsulta.append("%'");
            strbConsulta.append(" ORDER BY usuarioSistema.nome");

            // Recupera os dados por página
            lstRetorno = this.obterPorPagina(intNumeroPagina, intMaximoPagina, strbConsulta.toString());
            // Realiza iteração da colação para montar a chave de consulta (login+nome)
            Iterator itrRetorno = lstRetorno.iterator();
            lstRetorno = new ArrayList();
            while (itrRetorno.hasNext())
            {
                UsuarioSistema objUsuarioSistema = (UsuarioSistema) itrRetorno.next();
                objUsuarioSistema.setChaveConsulta(objUsuarioSistema.getIdentificador() + ";" + objUsuarioSistema.getNome());
                lstRetorno.add(objUsuarioSistema);
            }
        }
        catch (CDException cde)
        {
            throw new DAOException("Ocorreu um erro ao consultar registros na classe " + this.strNomeClasse, cde);
        }
        return lstRetorno;
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
    public String excluirImpl(Object objUsuarioSistema) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Retorna consulta de exclusão
        return " FROM"
                + " UsuarioSistema usuarioSistema"
                + " WHERE"
                + " usuarioSistema.identificador = "
                + ((UsuarioSistema) objUsuarioSistema).getIdentificador();
    }

    /* (non-Javadoc)
     * @see br.gov.camara.negocio.util.ConsultaComum#consultar(br.gov.camara.negocio.util.Consulta, int, int)
     */
    public List consultar(Consulta objConsulta, int intNumeroPagina, int intMaximoPagina) throws CDException
    {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see br.gov.camara.negocio.util.ConsultaComum#obterTotalRegistros(br.gov.camara.negocio.util.Consulta)
     */
    public int obterTotalRegistros(Consulta objConsulta) throws CDException
    {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * Obtém todos os registros por página
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public List obterPorPagina(int intNumeroPagina, int intMaximoPagina, UsuarioSistema objUsuarioSistema) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        List lstRetorno = null;

        try
        {
            // Monta query
            StringBuffer strbConsulta = new StringBuffer();

            strbConsulta.append(" FROM");
            strbConsulta.append(" UsuarioSistema usuarioSistema");
            strbConsulta.append(" WHERE 0=0");

            if (objUsuarioSistema.getIdentificador() != null)
            {
                strbConsulta.append(" AND usuarioSistema.identificador = ");
                strbConsulta.append(objUsuarioSistema.getIdentificador().intValue());
            }
            if (objUsuarioSistema.getNome() != null)
            {
                strbConsulta.append(" AND usuarioSistema.nome LIKE '%");
                strbConsulta.append(objUsuarioSistema.getNome());
                strbConsulta.append("%'");
            }
            if (objUsuarioSistema.getLogin() != null)
            {
                strbConsulta.append(" AND usuarioSistema.login LIKE '%");
                strbConsulta.append(objUsuarioSistema.getNome());
                strbConsulta.append("%'");
            }
            strbConsulta.append(" ORDER BY usuarioSistema.nome ASC");

            // Recupera os dados 
            lstRetorno = this.obterPorPagina(intNumeroPagina, intMaximoPagina, strbConsulta.toString());
            Iterator itrRetorno = lstRetorno.iterator();
            lstRetorno = new ArrayList();
            while (itrRetorno.hasNext())
            {
                UsuarioSistema objUsuario = (UsuarioSistema) itrRetorno.next();
                objUsuario.setChaveConsulta(objUsuario.getIdentificador() + ";" + objUsuario.getNome());
                lstRetorno.add(objUsuario);
            }
        }
        catch (DAOException daoe)
        {
            throw new DAOException("Ocorreu um erro ao consultar registros na classe " + this.strNomeClasse, daoe);
        }
        return lstRetorno;
    }

    /**
     * Obtém total de registros
     * 
     * @return int Contendo o total de registro da consulta
     *
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public int obterTotalRegistros(UsuarioSistema objUsuarioSistema) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }
        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" SELECT COUNT(*)");
        strbConsulta.append(" FROM");
        strbConsulta.append(" UsuarioSistema usuarioSistema");
        strbConsulta.append(" WHERE 0=0");

        if (objUsuarioSistema.getIdentificador() != null)
        {
            strbConsulta.append(" AND usuarioSistema.identificador = ");
            strbConsulta.append(objUsuarioSistema.getIdentificador().intValue());
        }
        if (objUsuarioSistema.getNome() != null)
        {
            strbConsulta.append(" AND usuarioSistema.nome LIKE '%");
            strbConsulta.append(objUsuarioSistema.getNome());
            strbConsulta.append("%'");
        }
        if (objUsuarioSistema.getLogin() != null)
        {
            strbConsulta.append(" AND usuarioSistema.login LIKE '%");
            strbConsulta.append(objUsuarioSistema.getNome());
            strbConsulta.append("%'");
        }

        // Retorna
        return this.obterTotalRegistros(strbConsulta.toString());
    }

    /**
     * Obtém lista de registros pelo nome por página
     *
     * @param String Nome a ser consultado
     * @param int Número da página a ser consultada
     * @param int Máximo de registro por página
     * 
     * @return Object POJO representando o registro obtido
     *
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public boolean validarPermissaoFuncionalidade(String strIdentificadorUsuario, String strIdentificadorGrupoUsuario, String strObjetoControladoFuncionalidade) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        boolean blnTemPermissao = false;

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        // Monta consulta das permissões para o usuário
        strbConsulta.append(this.obterQueryPerfisFuncionalidade(strObjetoControladoFuncionalidade));

        // Recupera os dados
        List lstRetorno = this.obter(strbConsulta.toString());
        if (lstRetorno != null && lstRetorno.size() > 0)
        {
            // Foram encontrados perfis associados a funcionalidade informada

            String strCodigosPerfisFuncionalidade = this.obterStringListaPerfilSistema(lstRetorno, ", ");

            // Limpa StringBuffer
            strbConsulta.setLength(0);

            // Existem perfis pais ???
            strbConsulta.append(" SELECT perfil.perfilAgrupador ");
            strbConsulta.append(" FROM PerfilSistema perfil ");
            strbConsulta.append(" WHERE perfil.identificador IN (");
            strbConsulta.append(strCodigosPerfisFuncionalidade);
            strbConsulta.append(")");

            lstRetorno = this.obter(strbConsulta.toString());
            if (lstRetorno != null && lstRetorno.size() > 0)
            {
                strCodigosPerfisFuncionalidade += ", " + this.obterStringListaPerfilSistema(lstRetorno, ", ");
            }

            // Limpa StringBuffer
            strbConsulta.setLength(0);

            // Verifica se o usuário está associado ao perfil
            strbConsulta.append(" FROM UsuarioPerfilSistema usuarioPerfilSistema");
            strbConsulta.append(" WHERE usuarioPerfilSistema.usuarioSistema.identificador = ");
            strbConsulta.append(strIdentificadorUsuario);
            strbConsulta.append(" AND usuarioPerfilSistema.perfilSistema.identificador IN (");
            strbConsulta.append(strCodigosPerfisFuncionalidade);
            strbConsulta.append(" )");

            List lstPerfisUsuario = null;

            lstPerfisUsuario = this.obter(strbConsulta.toString());
            if (lstPerfisUsuario != null && lstPerfisUsuario.size() > 0)
            {
                blnTemPermissao = true;
            }
            else if (strIdentificadorGrupoUsuario != null && !"".equals(strIdentificadorGrupoUsuario))
            {
                // Limpa StringBuffer
                strbConsulta.setLength(0);

                strbConsulta.append(" SELECT perfil FROM PerfilSistema perfil");
                strbConsulta.append(" JOIN perfil.grupos grupo");
                strbConsulta.append(" WHERE grupo.codigo = ");
                strbConsulta.append(strIdentificadorGrupoUsuario);
                strbConsulta.append(" AND perfil.identificador IN (");
                strbConsulta.append(strCodigosPerfisFuncionalidade);
                strbConsulta.append(" )");

                List lstPerfisGrupo = null;
                lstPerfisGrupo = this.obter(strbConsulta.toString());
                if (lstPerfisGrupo != null && lstPerfisGrupo.size() > 0)
                {
                    blnTemPermissao = true;
                }
            }

        }

        return blnTemPermissao;
    }

    /**
     * Obtém lista de registros pelo nome por página
     *
     * @param String Nome a ser consultado
     * @param int Número da página a ser consultada
     * @param int Máximo de registro por página
     * 
     * @return Object POJO representando o registro obtido
     *
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public boolean validarPermissaoFuncionalidade(String strIdentificadorUsuario, String strIdentificadorGrupoUsuario, String strObjetoControladoFuncionalidade, Map mapPerfisCriterioAcesso) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        boolean blnTemPermissao = false;

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        // Monta consulta das permissões para o usuário
        strbConsulta.append(this.obterQueryPerfisFuncionalidade(strObjetoControladoFuncionalidade));

        // Recupera os dados
        List lstRetorno = this.obter(strbConsulta.toString());
        if (lstRetorno != null && lstRetorno.size() > 0)
        {
            // Foram encontrados perfis associados a funcionalidade informada

            String strCodigosPerfisFuncionalidade = this.obterStringListaPerfilSistema(lstRetorno, ", ");

            // Limpa StringBuffer
            strbConsulta.setLength(0);

            // Existem perfis pais ???
            strbConsulta.append(" SELECT perfil.perfilAgrupador ");
            strbConsulta.append(" FROM PerfilSistema perfil ");
            strbConsulta.append(" WHERE perfil.identificador IN (");
            strbConsulta.append(strCodigosPerfisFuncionalidade);
            strbConsulta.append(")");

            lstRetorno = this.obter(strbConsulta.toString());
            if (lstRetorno != null && lstRetorno.size() > 0)
            {
                strCodigosPerfisFuncionalidade += ", " + this.obterStringListaPerfilSistema(lstRetorno, ", ");
            }

            //----------------------------------------------------------------------------------------------------------------------
            // PERMISSÕES DO PERFIL
            //----------------------------------------------------------------------------------------------------------------------

            // Limpa StringBuffer
            strbConsulta.setLength(0);

            // Verifica se o usuário está associado ao perfil
            strbConsulta.append(" SELECT usuarioPerfilSistema.perfilSistema");
            strbConsulta.append(" FROM UsuarioPerfilSistema usuarioPerfilSistema");
            strbConsulta.append(" WHERE usuarioPerfilSistema.usuarioSistema.identificador = ");
            strbConsulta.append(strIdentificadorUsuario);
            strbConsulta.append(" AND usuarioPerfilSistema.perfilSistema.identificador IN (");
            strbConsulta.append(strCodigosPerfisFuncionalidade);
            strbConsulta.append(" )");

            List lstPerfisUsuario = null;

            lstPerfisUsuario = this.obter(strbConsulta.toString());
            if (lstPerfisUsuario != null && lstPerfisUsuario.size() > 0)
            {
                //blnTemPermissao = true;
                blnTemPermissao = this.validarCriteriosAcesso(lstPerfisUsuario, strIdentificadorUsuario, mapPerfisCriterioAcesso);
            }
            else if (strIdentificadorGrupoUsuario != null && !"".equals(strIdentificadorGrupoUsuario))
            {
                // Limpa StringBuffer
                strbConsulta.setLength(0);

                strbConsulta.append(" SELECT perfil FROM PerfilSistema perfil");
                strbConsulta.append(" JOIN perfil.grupos grupo");
                strbConsulta.append(" WHERE grupo.codigo = ");
                strbConsulta.append(strIdentificadorGrupoUsuario);
                strbConsulta.append(" AND perfil.identificador IN (");
                strbConsulta.append(strCodigosPerfisFuncionalidade);
                strbConsulta.append(" )");

                List lstPerfisGrupo = null;
                lstPerfisGrupo = this.obter(strbConsulta.toString());
                if (lstPerfisGrupo != null && lstPerfisGrupo.size() > 0)
                {
                    //blnTemPermissao = true;
                    blnTemPermissao = this.validarCriteriosAcessoGrupo(lstPerfisGrupo, strIdentificadorUsuario, mapPerfisCriterioAcesso);
                }
            }
            //----------------------------------------------------------------------------------------------------------------------
            // PERMISSÕES DO AGRUPADOR
            //
            // Se não permitido, verifica se usuário está associado a algum agrupador de perfil ou se o agrupador possui algum grupo
            //----------------------------------------------------------------------------------------------------------------------
            if (!blnTemPermissao)
            {
                strbConsulta.setLength(0);
                // Consulta os perfis associados ao agrupador e que o usuário faça parte
                strbConsulta.append(" SELECT perfilSistemaAgrupadorPerfil.perfilSistema");
                strbConsulta.append(" FROM");
                strbConsulta.append(" AgrupadorPerfilUsuario agrupadorPerfilUsuario, PerfilSistemaAgrupadorPerfil perfilSistemaAgrupadorPerfil");
                strbConsulta.append(" WHERE agrupadorPerfilUsuario.agrupadorPerfil.identificador = perfilSistemaAgrupadorPerfil.agrupadorPerfil.identificador ");
                strbConsulta.append(" AND agrupadorPerfilUsuario.usuarioSistema.identificador = ");
                strbConsulta.append(strIdentificadorUsuario);
                strbConsulta.append(" AND perfilSistemaAgrupadorPerfil.perfilSistema.identificador IN ( ");
                strbConsulta.append(strCodigosPerfisFuncionalidade);
                strbConsulta.append(" )");

                List lstPerfisAgrupadores = this.obter(strbConsulta.toString());
                if (lstPerfisAgrupadores != null && lstPerfisAgrupadores.size() > 0)
                {
                    //blnTemPermissao = true;
                    blnTemPermissao = this.validarCriteriosAcesso(lstPerfisAgrupadores, strIdentificadorUsuario, mapPerfisCriterioAcesso);
                }
                else if (strIdentificadorGrupoUsuario != null && !"".equals(strIdentificadorGrupoUsuario))
                {
                    strbConsulta.setLength(0);
                    // Consulta os grupos associados ao agrupador e que o usuário faça parte
                    strbConsulta.append(" SELECT perfilSistemaAgrupadorPerfil.perfilSistema");
                    strbConsulta.append(" FROM");
                    strbConsulta.append(" AgrupadorPerfilGrupo agrupadorPerfilGrupo, PerfilSistemaAgrupadorPerfil perfilSistemaAgrupadorPerfil");
                    strbConsulta.append(" WHERE agrupadorPerfilGrupo.agrupadorPerfil.identificador = perfilSistemaAgrupadorPerfil.agrupadorPerfil.identificador ");
                    strbConsulta.append(" AND agrupadorPerfilGrupo.grupo.codigo = ");
                    strbConsulta.append(strIdentificadorGrupoUsuario);
                    strbConsulta.append(" AND perfilSistemaAgrupadorPerfil.perfilSistema.identificador IN ( ");
                    strbConsulta.append(strCodigosPerfisFuncionalidade);
                    strbConsulta.append(" )");
                    List lstAgrupadorPerfilGrupo = null;
                    lstAgrupadorPerfilGrupo = this.obter(strbConsulta.toString());
                    if (lstAgrupadorPerfilGrupo != null && lstAgrupadorPerfilGrupo.size() > 0)
                    {
                        //blnTemPermissao = true;
                        blnTemPermissao = this.validarCriteriosAcessoGrupo(lstAgrupadorPerfilGrupo, strIdentificadorUsuario, mapPerfisCriterioAcesso);
                    }
                }
            }

        }

        return blnTemPermissao;
    }

    private boolean validarCriteriosAcessoGrupo(List lstPerfisGrupo, String strIdentificadorUsuario, Map mapPerfisCriterioAcesso) throws DAOException
    {
        boolean blnTemPermissao = false;

        PerfilSistema objPerfilSistema = null;

        //List lstCriterios = new ArrayList();

        // Percorre lista enquanto não houver perfil que tenha permissão de acesso
        for (int i = 0; (i < lstPerfisGrupo.size() && !blnTemPermissao); i++)
        {
            objPerfilSistema = (PerfilSistema) lstPerfisGrupo.get(i);

            // Verifica se o perfil já se encontra no cache
            if (mapPerfisCriterioAcesso.containsKey(objPerfilSistema.getIdentificador()))
            {
                blnTemPermissao = ((Boolean) mapPerfisCriterioAcesso.get(objPerfilSistema.getIdentificador())).booleanValue();
            }
            else
            {
                blnTemPermissao = this.validarCriteriosAcessoGrupo(objPerfilSistema, strIdentificadorUsuario);
            }
        }
        mapPerfisCriterioAcesso.put(objPerfilSistema.getIdentificador(), new Boolean(blnTemPermissao));

        return blnTemPermissao;
    }

    public boolean validarCriteriosAcessoGrupo(PerfilSistema objPerfilSistema, String strIdentificadorUsuario) throws DAOException
    {
        boolean blnTemPermissao = false;
        boolean blnTemPermissaoPreRequisito = true;

        CriterioAcessoPerfilDAO objCriterioAcessoPerfilDAO = new CriterioAcessoPerfilDAO(this.nomeConexao);
        CriterioAcessoPerfil objCriterioAcessoPerfil = new CriterioAcessoPerfil();
        UsuarioCriterioAcessoPerfil objUsuarioCriterioAcessoPerfil = new UsuarioCriterioAcessoPerfil();

        List lstCriterios = new ArrayList();

        //-----------------------------------------------------------------------------------
        //---------------- Verifica critérios que são pré-requisitos ------------------------
        //-----------------------------------------------------------------------------------

        // Seta o perfil a ser consultado
        objCriterioAcessoPerfil.setPerfilSistema(objPerfilSistema);

        // Seta pre-requisito = S para obtenção dos critérios que são pre-requisito
        objCriterioAcessoPerfil.setPreRequisito("S");

        UsuarioSistema objUsuarioSistema = new UsuarioSistema();

        objUsuarioSistema.setIdentificador(new Integer(strIdentificadorUsuario));

        objUsuarioCriterioAcessoPerfil.setUsuarioSistema(objUsuarioSistema);

        // Obtém lista de critérios do perfil/usuário que são pre-requisitos
        lstCriterios = objCriterioAcessoPerfilDAO.obterPeloPerfilSistemaPreRequisitoValorado(objCriterioAcessoPerfil);

        // Verifica se possui algum pre-requisito e se esses são válidos
        if (lstCriterios != null && lstCriterios.size() > 0)
        {
            // Percorre lista de critérios
            for (int j = 0; (j < lstCriterios.size() && blnTemPermissaoPreRequisito); j++)
            {
                objCriterioAcessoPerfil = (CriterioAcessoPerfil) lstCriterios.get(j);
                objUsuarioCriterioAcessoPerfil.setCriterioAcessoPerfil(objCriterioAcessoPerfil);

                String strValorCriterio = this.obterValorCriterio(objUsuarioCriterioAcessoPerfil);

                // Verifica se o critério de acesso possui algum valor padrão e 
                // compara se o valor padrão é igual ao valor que o usuário possui
                if (objCriterioAcessoPerfil.getValorCriterioAcessoPerfil() != null)
                {
                    if (!objCriterioAcessoPerfil.getValorCriterioAcessoPerfil().equals(strValorCriterio))
                    {
                        blnTemPermissaoPreRequisito = false;
                    }
                }
            }
        }

        //---------------------------------------------------------------------------------------
        //---------------- Verifica critérios que NÃO são pré-requisitos ------------------------
        //---------------------------------------------------------------------------------------
        if (blnTemPermissaoPreRequisito)
        {
            objCriterioAcessoPerfil = new CriterioAcessoPerfil();

            // Seta o perfil a ser consultado
            objCriterioAcessoPerfil.setPerfilSistema(objPerfilSistema);

            // Seta pre-requisito = S para obtenção dos critérios que não são pre-requisito
            objCriterioAcessoPerfil.setPreRequisito("N");

            // Obtém lista de critérios do perfil/usuário que não são pre-requisitos
            lstCriterios = objCriterioAcessoPerfilDAO.obterPeloPerfilSistemaPreRequisitoValorado(objCriterioAcessoPerfil);

            if (lstCriterios != null && lstCriterios.size() > 0)
            {
                // Percorre lista de critérios
                for (int j = 0; (j < lstCriterios.size() && !blnTemPermissao); j++)
                {
                    objCriterioAcessoPerfil = (CriterioAcessoPerfil) lstCriterios.get(j);
                    objUsuarioCriterioAcessoPerfil.setCriterioAcessoPerfil(objCriterioAcessoPerfil);

                    String strValorCriterio = this.obterValorCriterio(objUsuarioCriterioAcessoPerfil);

                    // Verifica se o critério de acesso possui algum valor padrão e 
                    // compara se o valor padrão é igual ao valor que o usuário possui
                    if (objCriterioAcessoPerfil.getValorCriterioAcessoPerfil() != null)
                    {
                        if (objCriterioAcessoPerfil.getValorCriterioAcessoPerfil().equals(strValorCriterio))
                        {
                            blnTemPermissao = true;
                        }
                    }
                }
            }
            else
            {
                blnTemPermissao = true;
            }
        }

        return blnTemPermissao && blnTemPermissaoPreRequisito;
    }

    private boolean validarCriteriosAcesso(List lstPerfis, String strIdentificadorUsuario, Map mapPerfisCriterioAcesso) throws DAOException
    {
        boolean blnTemPermissao = false;

        PerfilSistema objPerfilSistema = null;

        // Percorre lista enquanto não houver perfil que tenha permissão de acesso
        for (int i = 0; (i < lstPerfis.size() && !blnTemPermissao); i++)
        {
            objPerfilSistema = (PerfilSistema) lstPerfis.get(i);

            // Verifica se o perfil já se encontra no cache
            if (mapPerfisCriterioAcesso.containsKey(objPerfilSistema.getIdentificador()))
            {
                blnTemPermissao = ((Boolean) mapPerfisCriterioAcesso.get(objPerfilSistema.getIdentificador())).booleanValue();
            }
            else
            {
                UsuarioSistema objUsuarioSistema = new UsuarioSistema();
                objUsuarioSistema.setIdentificador(new Integer(strIdentificadorUsuario));
                blnTemPermissao = this.validarCriteriosAcesso(objPerfilSistema, objUsuarioSistema);
            }
        }
        mapPerfisCriterioAcesso.put(objPerfilSistema.getIdentificador(), new Boolean(blnTemPermissao));

        return blnTemPermissao;
    }

    public boolean validarCriteriosAcesso(PerfilSistema objPerfilSistema, UsuarioSistema objUsuarioSistema) throws DAOException
    {
        boolean blnTemPermissao = false;
        boolean blnTemPermissaoPreRequisito = true;

        UsuarioCriterioAcessoPerfilDAO objUsuarioCriterioAcessoPerfilDAO = new UsuarioCriterioAcessoPerfilDAO(this.nomeConexao);
        UsuarioCriterioAcessoPerfil objUsuarioCriterioAcessoPerfil = new UsuarioCriterioAcessoPerfil();
        CriterioAcessoPerfil objCriterioAcessoPerfil = new CriterioAcessoPerfil();

        List lstCriterios = new ArrayList();

        //-----------------------------------------------------------------------------------
        //---------------- Verifica critérios que são pré-requisitos ------------------------
        //-----------------------------------------------------------------------------------

        // Seta o perfil a ser consultado
        objCriterioAcessoPerfil.setPerfilSistema(objPerfilSistema);

        // Seta pre-requisito = S para obtenção dos critérios que são pre-requisito
        objCriterioAcessoPerfil.setPreRequisito("S");

        objUsuarioCriterioAcessoPerfil.setCriterioAcessoPerfil(objCriterioAcessoPerfil);

        // Seta o usuário a ser consultado
        objUsuarioCriterioAcessoPerfil.setUsuarioSistema(objUsuarioSistema);

        // Obtém lista de critérios do perfil/usuário que são pre-requisitos
        lstCriterios = objUsuarioCriterioAcessoPerfilDAO.obterPeloPerfilUsuarioSistemaPreRequisito(objUsuarioCriterioAcessoPerfil);

        // Verifica se possui algum pre-requisito e se esses são válidos
        if (lstCriterios != null && lstCriterios.size() > 0)
        {
            // Percorre lista de critérios
            for (int j = 0; (j < lstCriterios.size() && blnTemPermissaoPreRequisito); j++)
            {
                objUsuarioCriterioAcessoPerfil = (UsuarioCriterioAcessoPerfil) lstCriterios.get(j);
                String strValoracaoAnterior = objUsuarioCriterioAcessoPerfil.getValoracaoCriterioAcesso();

                String strValorCriterio = this.obterValorCriterio(objUsuarioCriterioAcessoPerfil);

                // Verifica se o critério de acesso possui algum valor padrão e 
                // compara se o valor padrão é igual ao valor que o usuário possui
                if (objUsuarioCriterioAcessoPerfil.getCriterioAcessoPerfil().getValorCriterioAcessoPerfil() != null)
                {
                    if (!objUsuarioCriterioAcessoPerfil.getCriterioAcessoPerfil().getValorCriterioAcessoPerfil().equals(strValorCriterio))
                    {
                        blnTemPermissaoPreRequisito = false;
                    }
                }
                else if (!strValorCriterio.equals(strValoracaoAnterior))
                {
                    blnTemPermissaoPreRequisito = false;
                }
            }
        }

        //---------------------------------------------------------------------------------------
        //---------------- Verifica critérios que NÃO são pré-requisitos ------------------------
        //---------------------------------------------------------------------------------------
        if (blnTemPermissaoPreRequisito)
        {
            objUsuarioCriterioAcessoPerfil = new UsuarioCriterioAcessoPerfil();
            // Seta o perfil a ser consultado
            objCriterioAcessoPerfil.setPerfilSistema(objPerfilSistema);

            // Seta pre-requisito = S para obtenção dos critérios que não são pre-requisito
            objCriterioAcessoPerfil.setPreRequisito("N");

            objUsuarioCriterioAcessoPerfil.setCriterioAcessoPerfil(objCriterioAcessoPerfil);

            // Seta o usuário a ser consultado
            objUsuarioCriterioAcessoPerfil.setUsuarioSistema(objUsuarioSistema);

            // Obtém lista de critérios do perfil/usuário que não são pre-requisitos
            lstCriterios = objUsuarioCriterioAcessoPerfilDAO.obterPeloPerfilUsuarioSistemaPreRequisito(objUsuarioCriterioAcessoPerfil);

            if (lstCriterios != null && lstCriterios.size() > 0)
            {
                // Percorre lista de critérios
                for (int j = 0; (j < lstCriterios.size() && !blnTemPermissao); j++)
                {
                    objUsuarioCriterioAcessoPerfil = (UsuarioCriterioAcessoPerfil) lstCriterios.get(j);
                    String strValoracaoAnterior = objUsuarioCriterioAcessoPerfil.getValoracaoCriterioAcesso();

                    String strValorCriterio = this.obterValorCriterio(objUsuarioCriterioAcessoPerfil);

                    // Verifica se o critério de acesso possui algum valor padrão e 
                    // compara se o valor padrão é igual ao valor que o usuário possui
                    if (objUsuarioCriterioAcessoPerfil.getCriterioAcessoPerfil().getValorCriterioAcessoPerfil() != null)
                    {
                        if (objUsuarioCriterioAcessoPerfil.getCriterioAcessoPerfil().getValorCriterioAcessoPerfil().equals(strValorCriterio))
                        {
                            blnTemPermissao = true;
                        }
                    }
                    else if (strValorCriterio.equals(strValoracaoAnterior))
                    {
                        blnTemPermissao = true;
                    }
                }
            }
            else
            {
                blnTemPermissao = true;
            }
        }

        return blnTemPermissao && blnTemPermissaoPreRequisito;
    }

    private String obterValorCriterio(UsuarioCriterioAcessoPerfil objUsuarioCriterioAcessoPerfil) throws DAOException
    {
        // Obtém classe implementadora do critério de acesso
        ClasseDinamica clsDinamica = new ClasseDinamica();
        br.gov.camara.seguranca.criterioacesso.CriterioAcesso objCriterio;
        String strValorCriterio = null;
        try
        {
            objCriterio = (br.gov.camara.seguranca.criterioacesso.CriterioAcesso) clsDinamica.obterInstancia(objUsuarioCriterioAcessoPerfil.getCriterioAcessoPerfil().getCriterioAcesso().getClasseImplementadora());
            strValorCriterio = objCriterio.getValorCriterioAcesso(objUsuarioCriterioAcessoPerfil.getUsuarioSistema().getIdentificador());
        }
        catch (CDException cde)
        {
            throw new DAOException(cde.getMessage(), cde.getCause());
        }
        return strValorCriterio;

    }

    private String obterQueryPerfisFuncionalidade(String strObjetoControlado)
    {

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" SELECT perfil");
        strbConsulta.append(" FROM PerfilSistema perfil");
        strbConsulta.append(" JOIN perfil.funcionalidades funcionalidades");
        strbConsulta.append(" WHERE  funcionalidades IN");
        strbConsulta.append(" (");
        strbConsulta.append("   FROM  FuncionalidadeSistema funcionalidade");
        strbConsulta.append("   WHERE funcionalidade.objetoControlado = '");
        strbConsulta.append(strObjetoControlado);
        strbConsulta.append("'");
        strbConsulta.append("   OR funcionalidade IN");
        strbConsulta.append("   (");
        strbConsulta.append("       SELECT funcionalidadeAgrupadora.funcionalidadeAgrupadora");
        strbConsulta.append("       FROM   FuncionalidadeSistema funcionalidadeAgrupadora");
        strbConsulta.append("       WHERE  funcionalidadeAgrupadora.objetoControlado = '");
        strbConsulta.append(strObjetoControlado);
        strbConsulta.append("'");
        strbConsulta.append("   )");
        strbConsulta.append(" )");

        return strbConsulta.toString();
    }

    private String obterStringListaPerfilSistema(List lstRetorno, String strSeparador)
    {
        // Valida parâmetros
        if (lstRetorno == null || lstRetorno.size() == 0)
        {
            return null;
        }
        if (strSeparador == null)
        {
            return null;
        }

        // Monta a relação dos identificadores dos perfis recuperados separados por vírgula
        String strCodigosPerfisFuncionalidade = strSeparador;

        Iterator itrPerfisFuncionalidade = lstRetorno.iterator();
        while (itrPerfisFuncionalidade.hasNext())
        {
            PerfilSistema objPerfilSistema = (PerfilSistema) itrPerfisFuncionalidade.next();
            if (strCodigosPerfisFuncionalidade.indexOf(", " + objPerfilSistema.getIdentificador() + ",") < 0)
            {
                strCodigosPerfisFuncionalidade += objPerfilSistema.getIdentificador() + ", ";
            }
        }

        if (strSeparador.equals(strCodigosPerfisFuncionalidade))
        {
            strCodigosPerfisFuncionalidade = "";
        }
        else
        {
            strCodigosPerfisFuncionalidade = strCodigosPerfisFuncionalidade.substring(2, strCodigosPerfisFuncionalidade.length() - 2);
        }

        return strCodigosPerfisFuncionalidade;
    }

    /**
     * Método que pede a inicialização da propriedade Grupo, que é lazy
     * 
     * @param UsuarioSistema Classe que tem o atributo que vai ser recuperado
     * 
     * @see br.gov.camara.negocio.DAO#inicializarRelacionamento(java.lang.Object)
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public void inicializarGrupo(UsuarioSistema objUsuarioSistema) throws DAOException
    {
        this.inicializarRelacionamento(objUsuarioSistema.getGrupo());
    }

    /**
     *  Método que pede a inicialização da propriedade Grupo, que é lazy
     * 
     * @param List  Lista de objetos que tem o atributo que vai ser recuperado
     * 
     * @see br.gov.camara.negocio.DAO#inicializarRelacionamento(java.lang.Object)
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public void inicializarGrupo(List lstUsuarioSistema) throws DAOException
    {
        Iterator itrUsuarioSistema = lstUsuarioSistema.iterator();
        while (itrUsuarioSistema.hasNext())
        {
            this.inicializarGrupo(((UsuarioSistema) itrUsuarioSistema.next()));
        }
    }

    /**
     * Método que pede a inicialização da propriedade UsuarioSistema, que é lazy
     * 
     * @param UsuarioSistema Classe que tem o atributo que vai ser recuperado
     * 
     * @see br.gov.camara.negocio.DAO#inicializarRelacionamento(java.lang.Object)
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public void inicializarUsuarioSistema(UsuarioSistema objUsuarioSistema) throws DAOException
    {
        this.inicializarRelacionamento(objUsuarioSistema);
    }

}
