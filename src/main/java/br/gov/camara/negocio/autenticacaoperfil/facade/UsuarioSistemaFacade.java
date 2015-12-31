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

package br.gov.camara.negocio.autenticacaoperfil.facade;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.gov.camara.biblioteca.util.Copia;
import br.gov.camara.exception.CDException;
import br.gov.camara.negocio.DAO;
import br.gov.camara.negocio.Facade;
import br.gov.camara.negocio.autenticacaoperfil.dao.UsuarioSistemaDAO;
import br.gov.camara.negocio.autenticacaoperfil.pojo.UsuarioSistema;
import br.gov.camara.negocio.autenticacaoperfil.pojo.UsuarioSistemaVisualizacao;

/**
 * Facade para a exibi��o do curr�culo de determinada pessoa
 * 
 */
public class UsuarioSistemaFacade extends Facade
{
    // Vari�veis de inst�ncia
    private static Log log = LogFactory.getLog(UsuarioSistemaFacade.class);

    /**
     * Construtor Default
     */
    public UsuarioSistemaFacade()
    {
        super();
    }

    /**
     * @param nomeConexao
     *
     */
    public UsuarioSistemaFacade(String nomeConexao)
    {
        super(nomeConexao);
    }

    public static final String nomeConexaoSeguranca = "conexaoSeguranca";

    /**
     * Obt�m o usu�rio pela chave
     *
     * @param strChave Chave do registro a ser obtido
     * 
     * @return UsuarioSistema POJO representando o registro obtido
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public UsuarioSistema obterPelaChave(String strChave) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m os registros
        UsuarioSistemaDAO objUsuarioSistemaDAO = new UsuarioSistemaDAO(obterNomeConexao());
        UsuarioSistema objUsuarioSistema = null;
        try
        {
            objUsuarioSistema = (UsuarioSistema) objUsuarioSistemaDAO.obterPelaChave(strChave);
            if (objUsuarioSistema != null)
            {
                objUsuarioSistemaDAO.inicializarGrupo(objUsuarioSistema);
            }
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar(obterNomeConexao());
        }
        return objUsuarioSistema;
    }

    /**
     * Obt�m o usu�rio pela chave
     *
     * @param strLogin login do registro a ser obtido
     * 
     * @return UsuarioSistema POJO representando o registro obtido
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public UsuarioSistema obterPeloLogin(String strLogin) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m os registros
        UsuarioSistemaDAO objUsuarioSistemaDAO = new UsuarioSistemaDAO(obterNomeConexao());
        UsuarioSistema objUsuarioSistema = null;
        try
        {
            objUsuarioSistema = (UsuarioSistema) objUsuarioSistemaDAO.obterPeloLogin(strLogin);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar(obterNomeConexao());
        }
        return objUsuarioSistema;
    }

    /**
     * Obt�m lista de registros pelo nome por pagina  
     *
     * @param String nome a ser consultado
     * @param int n�mero da p�gina a ser consultada
     * @param int m�ximo de registro por p�gina
     * 
     * @return List Lista com os POJOs representando o registros obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public List obterPeloNomePorPagina(String strNome, int intNumeroPagina, int intMaximoPagina) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m os registros
        UsuarioSistemaDAO objUsuarioSistemaDAO = new UsuarioSistemaDAO(obterNomeConexao());
        List lstUsuarioSistema = null;
        try
        {
            lstUsuarioSistema = objUsuarioSistemaDAO.obterPeloNomePorPagina(strNome, intNumeroPagina, intMaximoPagina);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar(obterNomeConexao());
        }
        return lstUsuarioSistema;
    }

    /**
     * Obt�m todos os registro por p�gina 
     *
     * @param int n�mero da p�gina a ser consultada
     * @param int m�ximo de registro por p�gina
     * 
     * @return List Lista com os POJOs representando o registros obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public List obterTodosPorPagina(int intNumeroPagina, int intMaximoPagina) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m os registros
        UsuarioSistemaDAO objUsuarioSistemaDAO = new UsuarioSistemaDAO(obterNomeConexao());
        List lstUsuarioSistema = null;
        try
        {
            lstUsuarioSistema = objUsuarioSistemaDAO.obterTodosPorPagina(intNumeroPagina, intMaximoPagina);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar(obterNomeConexao());
        }
        return lstUsuarioSistema;
    }

    /**
     * Obt�m total de registro
     *
     * @return int Total de registros
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public int obterTotalRegistros() throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m os registros
        UsuarioSistemaDAO objUsuarioSistemaDAO = new UsuarioSistemaDAO(obterNomeConexao());
        int intTotalRegistros = 0;
        try
        {
            intTotalRegistros = objUsuarioSistemaDAO.obterTotalRegistros();
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar(obterNomeConexao());
        }
        return intTotalRegistros;
    }

    /**
     * Obt�m total de registro pelo nome
     *
     * @param String Nome a ser consultado
     * 
     * @return int Contendo o total de registros
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public int obterTotalRegistrosPeloNome(String strNome) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m os registros
        UsuarioSistemaDAO objUsuarioSistemaDAO = new UsuarioSistemaDAO(obterNomeConexao());
        int intTotalRegistros = 0;
        try
        {
            intTotalRegistros = objUsuarioSistemaDAO.obterTotalRegistrosPeloNome(strNome);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar(obterNomeConexao());
        }
        return intTotalRegistros;
    }

    /**
     * Obt�m lista de registros por p�gina  
     *
     * @param int n�mero da p�gina a ser consultada
     * @param int m�ximo de registro por p�gina
     * @param objUsuarioSistema parametro do usu�rio a serem consultados
     * 
     * @return List Lista com os POJOs representando o registros obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public List obterPorPagina(int intNumeroPagina, int intMaximoPagina, UsuarioSistema objUsuarioSistema) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m os registros
        UsuarioSistemaDAO objUsuarioSistemaDAO = new UsuarioSistemaDAO(obterNomeConexao());
        List lstUsuarioSistema = null;
        List lstRetorno = null;
        try
        {
            lstUsuarioSistema = objUsuarioSistemaDAO.obterPorPagina(intNumeroPagina, intMaximoPagina, objUsuarioSistema);
            lstRetorno = obterVisualizacao(lstUsuarioSistema);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar(obterNomeConexao());
        }
        return lstRetorno;
    }

    /**
     * Obt�m total de registro
     *
     * @param objUsuarioSistema parametros para consulta
     * 
     * @return int Contendo o total de registros
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public int obterTotalRegistros(UsuarioSistema objUsuarioSistema) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m os registros
        UsuarioSistemaDAO objUsuarioSistemaDAO = new UsuarioSistemaDAO(obterNomeConexao());
        int intTotalRegistros = 0;
        try
        {
            intTotalRegistros = objUsuarioSistemaDAO.obterTotalRegistros(objUsuarioSistema);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar(obterNomeConexao());
        }
        return intTotalRegistros;
    }

    private List obterVisualizacao(List lstUsuarioSistema)
    {
        List lstRetorno = new ArrayList();
        for (int i = 0; i < lstUsuarioSistema.size(); i++)
        {
            UsuarioSistema objUsuario = (UsuarioSistema) lstUsuarioSistema.get(i);
            UsuarioSistemaVisualizacao objUsuarioSistemaVisualizacao = new UsuarioSistemaVisualizacao();
            Copia.criar(objUsuario, objUsuarioSistemaVisualizacao);
            objUsuarioSistemaVisualizacao.setRetorno(objUsuario.getLogin() + ";" + objUsuario.getNome());
            lstRetorno.add(objUsuarioSistemaVisualizacao);
        }
        return lstRetorno;
    }

    /**
     * Verifica se existe um relacionamento entre um usu�rio e uma funcionalidade de sistema  
     *
     */
    public boolean verificarPermissaoUsuarioFuncionalidade(String strIdentificadorUsuario, String strIdentificadorGrupoUsuario, String strFuncionalidade, Map mapPerfisCriterioAcesso) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        boolean blnRetorno = false;

        try
        {
            UsuarioSistemaDAO usuarioSistemaDAO = new UsuarioSistemaDAO(nomeConexaoSeguranca);
            blnRetorno = usuarioSistemaDAO.validarPermissaoFuncionalidade(strIdentificadorUsuario, strIdentificadorGrupoUsuario, strFuncionalidade, mapPerfisCriterioAcesso);
        }
        catch (Exception daoe)
        {
            log.error("Ocorreu um erro verificando a permiss�o do usu�rio " + strIdentificadorUsuario + " na funcionalidade " + strFuncionalidade);
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar(nomeConexaoSeguranca);
        }
        return blnRetorno;
    }

    /**
     * Verifica se existe um relacionamento entre um usu�rio e uma funcionalidade de sistema  
     *
     */
    public boolean verificarPermissaoUsuarioFuncionalidade(String strIdentificadorUsuario, String strIdentificadorGrupoUsuario, String strFuncionalidade) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        boolean blnRetorno = false;

        try
        {
            UsuarioSistemaDAO usuarioSistemaDAO = new UsuarioSistemaDAO(nomeConexaoSeguranca);
            blnRetorno = usuarioSistemaDAO.validarPermissaoFuncionalidade(strIdentificadorUsuario, strIdentificadorGrupoUsuario, strFuncionalidade);
        }
        catch (Exception daoe)
        {
            log.error("Ocorreu um erro verificando a permiss�o do usu�rio " + strIdentificadorUsuario + " na funcionalidade " + strFuncionalidade);
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar(nomeConexaoSeguranca);
        }
        return blnRetorno;
    }

}
