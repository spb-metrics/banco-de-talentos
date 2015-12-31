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

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.gov.camara.exception.CDException;
import br.gov.camara.negocio.DAO;
import br.gov.camara.negocio.Facade;
import br.gov.camara.negocio.autenticacaoperfil.dao.GestorSistemaDAO;
import br.gov.camara.negocio.autenticacaoperfil.dao.SistemaDAO;
import br.gov.camara.negocio.autenticacaoperfil.pojo.GestorSistema;
import br.gov.camara.negocio.autenticacaoperfil.pojo.Sistema;
import br.gov.camara.negocio.autenticacaoperfil.pojo.UsuarioSistema;

/**
 * Facade para atributo de talento
 */
public class GestorSistemaFacade extends Facade
{
    // Vari�veis de inst�ncia
    private static Log log = LogFactory.getLog(GestorSistemaFacade.class);

    /**
     * Construtor Default
     */
    public GestorSistemaFacade()
    {
        super();
    }

    /**
     * @param nomeConexao
     *
     */
    public GestorSistemaFacade(String nomeConexao)
    {
        super(nomeConexao);
    }

    /**
     * Obt�m um registro a partir da chave
     *
     * @param strChave Chave do registro a ser obtido
     * 
     * @return GestorSistema POJO representando o registro obtido
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public List obterTodos() throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m o registro pela chave
        GestorSistemaDAO objGestorSistemaDAO = new GestorSistemaDAO(obterNomeConexao());
        List lstRetorno = null;
        try
        {
            lstRetorno = objGestorSistemaDAO.obterTodos();
            objGestorSistemaDAO.inicializarSistema(lstRetorno);
            objGestorSistemaDAO.inicializarUsuarioSistema(lstRetorno);

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
     * Obt�m um registro a partir da chave
     *
     * @param strChave Chave do registro a ser obtido
     * 
     * @return GestorSistema POJO representando o registro obtido
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public GestorSistema obterPelaChave(GestorSistema objGestorSistema) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m o registro pela chave
        GestorSistemaDAO objGestorSistemaDAO = new GestorSistemaDAO(obterNomeConexao());
        GestorSistema objRetorno = null;
        try
        {
            objRetorno = (GestorSistema) objGestorSistemaDAO.obterPelaChave(objGestorSistema);
            objGestorSistemaDAO.inicializarSistema(objRetorno);
            objGestorSistemaDAO.inicializarUsuarioSistema(objRetorno);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar(obterNomeConexao());
        }
        return objRetorno;
    }

    /**
     * Inclui um registro
     *
     * @param objGestorSistema POJO representando o objeto a ser inclu�do
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     *
     * @return retorna a chave do objeto que foi incluido
     *
     */
    public String incluir(GestorSistema objGestorSistema, UsuarioSistema objUsuarioSistema, String strObjetoControlado) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        String strChave = null;

        // Instancia DAO e inclui registro
        GestorSistemaDAO objGestorSistemaDAO = new GestorSistemaDAO(obterNomeConexao());
        UsuarioPerfilSistemaFacade objUsuarioPerfilSistemaFacade = new UsuarioPerfilSistemaFacade(obterNomeConexao());
       try
        {
            if (!isAdministradorSistema(objGestorSistema.getSistema(), objUsuarioSistema))
            {
                throw new CDException("Somente administradores deste sistema podem incluir um Gestor");
            }
            DAO.iniciarTransacao(obterNomeConexao());
         
            objUsuarioPerfilSistemaFacade.realizarInclusaoPerfil(objGestorSistema.getUsuarioSistema(), strObjetoControlado);
            
            strChave = objGestorSistemaDAO.incluir(objGestorSistema);
            
            DAO.realizarTransacao(obterNomeConexao());
        }
        catch (Exception daoe)
        {
            DAO.desfazerTransacao(obterNomeConexao());
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar(obterNomeConexao());
        }

        return strChave;
    }

    /**
     * Verifica se o sistema possui algum administrador
     *
     * @param objGestorSistema POJO representando o objeto a ser inclu�do
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     *
     * @return retorna a chave do objeto que foi incluido
     *
     */
    public boolean PossuiAdministrador(Sistema objSistema) throws CDException
    {
        boolean blnRetorno = false;
        GestorSistemaDAO objGestorSistemaDAO = new GestorSistemaDAO(obterNomeConexao());
        SistemaDAO objSistemaDAO = new SistemaDAO(obterNomeConexao());
        try
        {

            objSistema = (Sistema) objSistemaDAO.obterPelaChave(objSistema);
            if (objSistema.getSistemaAgrupador() == null)
            {
                GestorSistema objGestorSistema = new GestorSistema();
                objGestorSistema.setSistema(objSistema);
                List lstGestor = objGestorSistemaDAO.obterPeloSistema(objGestorSistema);
                if (lstGestor.size() > 0)
                {
                    for (int i = 0; i < lstGestor.size(); i++)
                    {
                        GestorSistema objGestor = (GestorSistema) lstGestor.get(i);
                        if (objGestor != null && "S".equals(objGestor.getIndicativoAdministrador()))
                        {
                            blnRetorno = true;
                        }
                    }
                }
            }
            while (objSistema.getSistemaAgrupador() != null && blnRetorno == false)
            {
                GestorSistema objGestorSuperior = new GestorSistema();
                objGestorSuperior.setSistema(objSistema.getSistemaAgrupador());
                List lstGestores = objGestorSistemaDAO.obterPeloSistema(objGestorSuperior);
                for (int i = 0; i < lstGestores.size(); i++)
                {
                    objGestorSuperior = (GestorSistema) lstGestores.get(i);
                    if (objGestorSuperior != null && "S".equals(objGestorSuperior.getIndicativoAdministrador()))
                    {
                        blnRetorno = true;
                    }
                }
                objSistema = (Sistema) objSistemaDAO.obterPelaChave(objGestorSuperior.getSistema());
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

        return blnRetorno;
    }

    /**
     * Verifica se o usu�rio est� cadastrado como gestor
     *
     * @param objGestorSistema POJO representando o objeto a ser inclu�do
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     *
     * @return retorna a chave do objeto que foi incluido
     *
     */
    public boolean isGestorSistema(Sistema objSistema, UsuarioSistema objUsuarioSistema) throws CDException
    {
        boolean blnRetorno = false;
        GestorSistemaDAO objGestorSistemaDAO = new GestorSistemaDAO(obterNomeConexao());
        GestorSistema objGestorSistema = new GestorSistema();
        objGestorSistema.setSistema(objSistema);
        objGestorSistema.setUsuarioSistema(objUsuarioSistema);
        try
        {
            if (objGestorSistemaDAO.isGestorSistema(objGestorSistema))
            {
                blnRetorno = true;
            }
            else
            {
                SistemaDAO objSistemaDAO = new SistemaDAO(obterNomeConexao());
                objGestorSistema.setSistema((Sistema) objSistemaDAO.obterPelaChave(objSistema));
                if (objGestorSistema.getSistema() != null && objGestorSistema.getSistema().getSistemaAgrupador() != null)
                {
                    blnRetorno = isGestorSistema(objGestorSistema.getSistema().getSistemaAgrupador(), objUsuarioSistema);
                }
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

        return blnRetorno;

    }

    /**
     * Verifica se o usu�rio est� cadastrado como gestor
     *
     * @param objGestorSistema POJO representando o objeto a ser inclu�do
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     *
     * @return retorna a chave do objeto que foi incluido
     *
     */
    public boolean isAdministradorSistema(Sistema objSistema, UsuarioSistema objUsuarioSistema) throws CDException
    {
        boolean blnRetorno = false;
        GestorSistemaDAO objGestorSistemaDAO = new GestorSistemaDAO(obterNomeConexao());
        GestorSistema objGestorSistema = new GestorSistema();
        objGestorSistema.setSistema(objSistema);
        objGestorSistema.setUsuarioSistema(objUsuarioSistema);
        try
        {
            if (objGestorSistemaDAO.isAdministradorSistema(objGestorSistema))
            {
                blnRetorno = true;
            }
            else
            {
                SistemaDAO objSistemaDAO = new SistemaDAO(obterNomeConexao());
                objGestorSistema.setSistema((Sistema) objSistemaDAO.obterPelaChave(objSistema));
                if (objGestorSistema.getSistema().getSistemaAgrupador() != null)
                {
                    blnRetorno = isAdministradorSistema(objGestorSistema.getSistema().getSistemaAgrupador(), objUsuarioSistema);
                }
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

        return blnRetorno;

    }

    /**
     * Verifica se o usu�rio est� cadastrado como gestor
     *
     * @param objGestorSistema POJO representando o objeto a ser inclu�do
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     *
     * @return retorna a chave do objeto que foi incluido
     *
     */
    public boolean isGestorDeAlgumSistema(UsuarioSistema objUsuarioSistema) throws CDException
    {
        boolean blnRetorno = false;
        GestorSistemaDAO objGestorSistemaDAO = new GestorSistemaDAO(obterNomeConexao());
        try
        {
            blnRetorno = objGestorSistemaDAO.isGestorDeAlgumSistema(objUsuarioSistema);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar(obterNomeConexao());
        }

        return blnRetorno;
    }

    /**
     * Verifica se o usu�rio est� cadastrado como gestor
     *
     * @param objGestorSistema POJO representando o objeto a ser inclu�do
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     *
     * @return retorna a chave do objeto que foi incluido
     *
     */
    public boolean isAdministradorDeAlgumSistema(UsuarioSistema objUsuarioSistema) throws CDException
    {
        boolean blnRetorno = false;
        GestorSistemaDAO objGestorSistemaDAO = new GestorSistemaDAO(obterNomeConexao());
        try
        {
            blnRetorno = objGestorSistemaDAO.isAdministradorDeAlgumSistema(objUsuarioSistema);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar(obterNomeConexao());
        }

        return blnRetorno;
    }

    /**
     * Altera um registro
     *
     * @param objGestorSistema POJO representando o objeto a ser inclu�do
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     *
     */
    public void alterar(GestorSistema objGestorSistema, UsuarioSistema objUsuarioSistema, String strObjControladoApoio, 
            String strObjControladoGestao) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e altera registro
        GestorSistemaDAO objGestorSistemaDAO = new GestorSistemaDAO(obterNomeConexao());
        UsuarioPerfilSistemaFacade objUsuarioPerfilSistemaFacade = new UsuarioPerfilSistemaFacade(obterNomeConexao());
        try
        {
            DAO.iniciarTransacao(obterNomeConexao());
            if (!isAdministradorSistema(objGestorSistema.getSistema(), objUsuarioSistema))
            {
                throw new CDException("Somente administradores deste sistema podem alterar um Gestor");
            }
            objGestorSistemaDAO.alterar(objGestorSistema);
            if (!PossuiAdministrador(objGestorSistema.getSistema()))
            {
                throw new CDException("N�o � poss�vel realizar a altera��o, pois o sistema deve ter ao menos um administrador");
            }
            
            if ("S".equals(objGestorSistema.getIndicativoAdministrador()))
            {
                objUsuarioPerfilSistemaFacade.realizarInclusaoPerfil(objGestorSistema.getUsuarioSistema(), strObjControladoApoio);
            }
            else
            {
                objUsuarioPerfilSistemaFacade.realizarInclusaoPerfil(objGestorSistema.getUsuarioSistema(), strObjControladoGestao);
                if (!objGestorSistemaDAO.isAdministradorDeAlgumSistema(objGestorSistema.getUsuarioSistema()))
                {
                    objUsuarioPerfilSistemaFacade.realizarExclusaoPerfil(objUsuarioSistema, strObjControladoApoio);
                }
            }
            
            DAO.realizarTransacao(obterNomeConexao());

        }
        catch (Exception daoe)
        {
            DAO.desfazerTransacao(obterNomeConexao());
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar(obterNomeConexao());
        }
    }

    /**
     * Exclui um registro
     *
     * @param objGestorSistema POJO representando o objeto a ser inclu�do
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     *
     */
    public void excluir(GestorSistema objGestorSistema, UsuarioSistema objUsuarioSistema, String strObjetoControlado) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e exclui registro
        GestorSistemaDAO objGestorSistemaDAO = new GestorSistemaDAO(obterNomeConexao());
        UsuarioPerfilSistemaFacade objUsuarioPerfilSistemaFacade = new UsuarioPerfilSistemaFacade(obterNomeConexao());
        try
        {
            DAO.iniciarTransacao(obterNomeConexao());
            
            objGestorSistema = (GestorSistema) objGestorSistemaDAO.obterPelaChave(objGestorSistema);
            
            if (!isAdministradorSistema(objGestorSistema.getSistema(), objUsuarioSistema))
            {
                throw new CDException("Somente administradores deste sistema podem realizar exclus�o");
            }
            objGestorSistemaDAO.excluir(objGestorSistema);
            if (!PossuiAdministrador(objGestorSistema.getSistema()))
            {
                throw new CDException("N�o � poss�vel realizar a exclus�o, pois o sistema deve ter ao menos um administrador");
            }
            
            if ("S".equals(objGestorSistema.getIndicativoAdministrador()) && 
                    !objGestorSistemaDAO.isAdministradorDeAlgumSistema(objGestorSistema.getUsuarioSistema()))
            {
                objUsuarioPerfilSistemaFacade.realizarExclusaoPerfil(objGestorSistema.getUsuarioSistema(), strObjetoControlado);
            }
            else if (!objUsuarioPerfilSistemaFacade.isGestorDeAlgumPerfil(objGestorSistema.getUsuarioSistema()) && 
                    !isGestorDeAlgumSistema(objGestorSistema.getUsuarioSistema()))
            {
                objUsuarioPerfilSistemaFacade.realizarExclusaoPerfil(objGestorSistema.getUsuarioSistema(), strObjetoControlado);
            }
            
            DAO.realizarTransacao(obterNomeConexao());

        }
        catch (Exception daoe)
        {
            DAO.desfazerTransacao(obterNomeConexao());
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar(obterNomeConexao());
        }
    }

    /**
     * Obt�m um registro a partir da chave
     *
     * @param strChave Chave do registro a ser obtido
     * 
     * @return GestorSistema POJO representando o registro obtido
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public List obterPeloSistema(GestorSistema objGestorSistema) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m o registro pela chave
        GestorSistemaDAO objGestorSistemaDAO = new GestorSistemaDAO(obterNomeConexao());
        List lstRetorno = null;
        try
        {
            lstRetorno = objGestorSistemaDAO.obterPeloSistema(objGestorSistema);
            objGestorSistemaDAO.inicializarSistema(lstRetorno);
            objGestorSistemaDAO.inicializarUsuarioSistema(lstRetorno);

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
     * Obt�m um registro a partir da chave
     *
     * @param strChave Chave do registro a ser obtido
     * 
     * @return GestorSistema POJO representando o registro obtido
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public List obterPeloUsuarioSistema(GestorSistema objGestorSistema) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m o registro pela chave
        GestorSistemaDAO objGestorSistemaDAO = new GestorSistemaDAO(obterNomeConexao());
        List lstRetorno = null;
        try
        {
            lstRetorno = objGestorSistemaDAO.obterPeloUsuarioSistema(objGestorSistema);
            objGestorSistemaDAO.inicializarSistema(lstRetorno);
            objGestorSistemaDAO.inicializarUsuarioSistema(lstRetorno);

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
     * Obt�m um registro a partir da chave
     *
     * @param strChave Chave do registro a ser obtido
     * 
     * @return GestorSistema POJO representando o registro obtido
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public List obterGestoresSistemaUsuarioLogado(UsuarioSistema objUsuarioLogado) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m o registro pela chave
        GestorSistemaDAO objGestorSistemaDAO = new GestorSistemaDAO(obterNomeConexao());
        List lstRetorno = null;
        try
        {
            lstRetorno = objGestorSistemaDAO.obterGestoresSistemaUsuarioLogado(objUsuarioLogado);
            objGestorSistemaDAO.inicializarSistema(lstRetorno);
            objGestorSistemaDAO.inicializarUsuarioSistema(lstRetorno);

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
}
