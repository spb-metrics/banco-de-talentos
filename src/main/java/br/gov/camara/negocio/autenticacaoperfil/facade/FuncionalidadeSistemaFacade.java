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

package br.gov.camara.negocio.autenticacaoperfil.facade;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import br.gov.camara.biblioteca.util.Copia;
import br.gov.camara.exception.CDException;
import br.gov.camara.negocio.DAO;
import br.gov.camara.negocio.Facade;
import br.gov.camara.negocio.autenticacaoperfil.dao.FuncionalidadeSistemaDAO;
import br.gov.camara.negocio.autenticacaoperfil.pojo.FuncionalidadeSistema;
import br.gov.camara.negocio.autenticacaoperfil.pojo.FuncionalidadeSistemaVisualizacao;
import br.gov.camara.negocio.autenticacaoperfil.pojo.Sistema;
import br.gov.camara.negocio.autenticacaoperfil.pojo.UsuarioSistema;

/**
 * Facade para atributo de talento
 */
public class FuncionalidadeSistemaFacade extends Facade
{
    private final int ELEMENTO_SUPERIOR = 1;
    private final int ELEMENTO_INFERIOR = 2;
    private List lstEncaminhamento = new ArrayList();
    private List lstAcao = new ArrayList();

    // Variáveis de instância
    private static Log log = LogFactory.getLog(FuncionalidadeSistemaFacade.class);

    /**
     * Obtém um registro a partir da chave
     *
     * @param objFuncionalidadeSistema POJO contendo a chave de pesquisa
     * 
     * @return FuncionalidadeSistema POJO representando o registro obtido
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public FuncionalidadeSistema obterPelaChave(FuncionalidadeSistema objFuncionalidadeSistema) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém o registro pela chave
        FuncionalidadeSistemaDAO objFuncionalidadeSistemaDAO = new FuncionalidadeSistemaDAO(obterNomeConexao());
        FuncionalidadeSistema objRetorno = null;
        try
        {
            objRetorno = (FuncionalidadeSistema) objFuncionalidadeSistemaDAO.obterPelaChave(objFuncionalidadeSistema);
            objFuncionalidadeSistemaDAO.inicializarSistema(objRetorno);
            if (objRetorno.getFuncionalidadeAgrupadora() != null)
            {
                objFuncionalidadeSistemaDAO.inicializarFuncionalidadeAgrupadora(objRetorno);
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
        return objRetorno;
    }

    /**
     * Inclui um registro, verificando se o usuário passado é administrador do sistema ou se o objeto 
     * controlado já está cadastrado
     *
     c     * @param objUsuarioSistema POJO representando os dados do usuário que será validado como administrador do sistema
     *
     * @return retorna a chave do objeto que foi incluido

     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public String incluir(FuncionalidadeSistema objFuncionalidadeSistema, UsuarioSistema objUsuarioSistema) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        String strChave = null;

        // Instancia DAO e inclui registro
        FuncionalidadeSistemaDAO objFuncionalidadeSistemaDAO = new FuncionalidadeSistemaDAO(obterNomeConexao());
        GestorSistemaFacade objGestorSistemaFacade = new GestorSistemaFacade(obterNomeConexao());
        try
        {
            // Verifica se o usuário é administrador do sistema
            if (!objGestorSistemaFacade.isAdministradorSistema(objFuncionalidadeSistema.getSistema(), objUsuarioSistema))
            {
                throw new CDException("Somente administradores deste sistema podem incluir uma Funcionalidade");
            }
            // Verifica se o objeto controlado existe
            else if (objFuncionalidadeSistemaDAO.obterPeloObjetoControlado(objFuncionalidadeSistema.getObjetoControlado()) != null)
            {
                throw new CDException("Já existe uma funcionalidade com o Objeto controlado informado");
            }

            // Realiza inclusão
            strChave = objFuncionalidadeSistemaDAO.incluir(objFuncionalidadeSistema);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar(obterNomeConexao());
        }

        return strChave;
    }

    /**
     * Altera um registro, verificando se o usuário passado é administrador do sistema ou se o objeto 
     * controlado já está cadastrado
     *
     * @param objFuncionalidadeSistema POJO representando o objeto a ser alterado
     * @param objUsuarioSistema POJO representando os dados do usuário que será validado como administrador do sistema
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     *
     */
    public void alterar(FuncionalidadeSistema objFuncionalidadeSistema, UsuarioSistema objUsuarioSistema) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e altera registro
        FuncionalidadeSistemaDAO objFuncionalidadeSistemaDAO = new FuncionalidadeSistemaDAO(obterNomeConexao());
        GestorSistemaFacade objGestorSistemaFacade = new GestorSistemaFacade(obterNomeConexao());
        try
        {
            // Verifica se o usuário é administrador do sistema
            if (!objGestorSistemaFacade.isAdministradorSistema(objFuncionalidadeSistema.getSistema(), objUsuarioSistema))
            {
                throw new CDException("Somente administradores deste sistema podem alterar uma Funcionalidade");
            }
            // Verifica se o objeto controlado existe
            else if (objFuncionalidadeSistemaDAO.isObjetoControladoCadastrado(objFuncionalidadeSistema))
            {
                throw new CDException("Já existe uma funcionalidade com o Objeto controlado informado");
            }

            // Realiza alteração
            objFuncionalidadeSistemaDAO.alterar(objFuncionalidadeSistema);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar(obterNomeConexao());
        }
    }

    /**
     * Altera a funcionalidade agrupadora das funcionalidades passadas na lista, 
     * verificando se o usuário passado é administrador do sistema ou se o objeto 
     * controlado já está cadastrado
     *
     * @param lstFuncionalidades Lista de funcionalidades que terão seu agrupador alterado 
     * @param objFuncAgrupadora POJO representando a funcionalidade agrupadora a ser atribuída
     * @param objUsuarioSistema POJO representando os dados do usuário que será validado como administrador do sistema
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     *
     */
    public void alterarFuncionalidadeSuperior(List lstFuncionalidades, FuncionalidadeSistema objFuncAgrupadora, UsuarioSistema objUsuarioSistema) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        try
        {
            DAO.iniciarTransacao(obterNomeConexao());
            for (int i = 0; i < lstFuncionalidades.size(); i++)
            {
                FuncionalidadeSistema objFunc = (FuncionalidadeSistema) lstFuncionalidades.get(i);

                // Obtém objeto a ser alterado
                FuncionalidadeSistema objFuncionalidadeSistema = obterPelaChave(objFunc);
                // Seta o agrupador
                objFuncionalidadeSistema.setFuncionalidadeAgrupadora(objFuncAgrupadora);
                // Realiza alteração 
                alterar(objFuncionalidadeSistema, objUsuarioSistema);

                objFuncionalidadeSistema = null;
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
     * Exclui um registro, verificando se o usuário passado é administrador do sistema 
     *
     * @param objFuncionalidadeSistema POJO representando o objeto a ser excluído
     * @param objUsuarioSistema POJO representando os dados do usuário que será validado como administrador do sistema
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     *
     */
    public void excluir(FuncionalidadeSistema objFuncionalidadeSistema, UsuarioSistema objUsuarioSistema) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e exclui registro
        FuncionalidadeSistemaDAO objFuncionalidadeSistemaDAO = new FuncionalidadeSistemaDAO(obterNomeConexao());
        GestorSistemaFacade objGestorSistemaFacade = new GestorSistemaFacade(obterNomeConexao());
        try
        {
            // Verifica se o usuário é administrador do sistema
            if (!objGestorSistemaFacade.isAdministradorSistema(objFuncionalidadeSistema.getSistema(), objUsuarioSistema))
            {
                throw new CDException("Somente administradores deste sistema podem excluir uma Funcionalidade");
            }

            // Realiza exclusão
            objFuncionalidadeSistemaDAO.excluir(objFuncionalidadeSistema);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar(obterNomeConexao());
        }
    }

    /**
     * Realiza exclusão de uma ou várias funcionalidades, 
     * verificando se o usuário passado é administrador do sistema
     *
     * @param objUsuarioSistema POJO representando os dados do usuário que será validado como administrador do sistema
     * @param strtFunc Objeto contendo as funcionalidades a serem excluídas
     *
     * @return Lista de funcionalidades excluídas
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public List excluir(UsuarioSistema objUsuarioLogado, StringTokenizer strtFunc) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        List lstRetorno = new ArrayList();

        try
        {
            DAO.iniciarTransacao(obterNomeConexao());
            while (strtFunc.hasMoreElements())
            {
                FuncionalidadeSistema objFuncionalidadeSistema = new FuncionalidadeSistema();

                // Obtém funcionalidade a ser excluída
                Integer intIdentificador = new Integer(strtFunc.nextElement().toString());
                objFuncionalidadeSistema.setIdentificador(intIdentificador);
                objFuncionalidadeSistema = obterPelaChave(objFuncionalidadeSistema);

                // Realiza exclusão da funcionalidade
                excluir(objFuncionalidadeSistema, objUsuarioLogado);
                lstRetorno.add(objFuncionalidadeSistema);
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
        return lstRetorno;
    }

    /**
     * Obtém o total de registros
     * 
     * @param objFuncionalidadeSistema POJO contendo os parâmetros da consulta
     * 
     * @return int Contendo o total de registros
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public int obterTotalRegistros(FuncionalidadeSistema objFuncionalidadeSistema) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém os registros da página
        FuncionalidadeSistemaDAO objFuncionalidadeSistemaDAO = new FuncionalidadeSistemaDAO(obterNomeConexao());
        int intTotalRegistros = 0;
        try
        {
            intTotalRegistros = objFuncionalidadeSistemaDAO.obterTotalRegistros(objFuncionalidadeSistema);
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
     * Obtém os registros de determinada página
     * 
     * @param intNumeroPagina Número da página a ser mostrada
     * @param intMaximoPagina Quantidade de registros por página
     * @param objFuncionalidadeSistema POJO contendo os parâmetros da consulta
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public List obterPorPagina(int intNumeroPagina, int intMaximoPagina, FuncionalidadeSistema objFuncionalidadeSistema) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém os registros da página
        FuncionalidadeSistemaDAO objFuncionalidadeSistemaDAO = new FuncionalidadeSistemaDAO(obterNomeConexao());
        List lstRetorno = null;
        try
        {
            lstRetorno = objFuncionalidadeSistemaDAO.obterPorPagina(intNumeroPagina, intMaximoPagina, objFuncionalidadeSistema);
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
     * Obtém todas as funcionalidades
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public List obterTodos() throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém os registros da página
        FuncionalidadeSistemaDAO objFuncionalidadeSistemaDAO = new FuncionalidadeSistemaDAO(obterNomeConexao());
        List lstRetorno = null;
        try
        {
            lstRetorno = objFuncionalidadeSistemaDAO.obterTodos();
        }
        catch (Exception e)
        {
            throw new CDException(e.getMessage(), e);
        }
        finally
        {
            DAO.desconectar(obterNomeConexao());
        }
        return lstRetorno;
    }

    /**
     * Obtém as funcionalidades filhas de uma determinada funcionalidade
     * 
     * @param objFuncionalidadeSistema POJO contendo os parâmetros da consulta
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public List obterPelaFuncionalidadeAgrupadora(FuncionalidadeSistema objFuncionalidadeSistema) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém os registros da página
        FuncionalidadeSistemaDAO objFuncionalidadeSistemaDAO = new FuncionalidadeSistemaDAO(obterNomeConexao());
        List lstRetorno = null;
        try
        {
            lstRetorno = objFuncionalidadeSistemaDAO.obterPelaFuncionalidadeAgrupadora(objFuncionalidadeSistema);
            lstRetorno = obterVisualizacao(lstRetorno);
        }
        catch (Exception e)
        {
            throw new CDException(e.getMessage(), e);
        }
        finally
        {
            DAO.desconectar(obterNomeConexao());
        }
        return lstRetorno;
    }

    /**
     * Obtém as funcionalidades filhas de uma determinada funcionalidade
     * 
     * @param intIdentificador objeto contendo a chave do agrupador
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public List obterPelaFuncionalidadeAgrupadora(Integer intIdentificador) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém os registros da página
        FuncionalidadeSistemaDAO objFuncionalidadeSistemaDAO = new FuncionalidadeSistemaDAO(obterNomeConexao());
        List lstRetorno = null;
        try
        {
            lstRetorno = objFuncionalidadeSistemaDAO.obterPelaFuncionalidadeAgrupadora(intIdentificador);
            objFuncionalidadeSistemaDAO.inicializarSistema(lstRetorno);
            lstRetorno = obterVisualizacao(lstRetorno);
        }
        catch (Exception e)
        {
            throw new CDException(e.getMessage(), e);
        }
        finally
        {
            DAO.desconectar(obterNomeConexao());
        }
        return lstRetorno;
    }

    /**
     * Obtém todas as funcionalidades quem não possuem pai (estão no topo da hierarquia)
     * 
     * @param objFuncionalidadeSistema objeto contendo os parâmetros da consulta
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public List obterPeloSistemaAgrupadorNull(FuncionalidadeSistema objFuncionalidadeSistema) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém os registros da página
        FuncionalidadeSistemaDAO objFuncionalidadeSistemaDAO = new FuncionalidadeSistemaDAO(obterNomeConexao());
        List lstRetorno = null;
        try
        {
            lstRetorno = objFuncionalidadeSistemaDAO.obterPeloSistemaAgrupadorNull(objFuncionalidadeSistema);
            lstRetorno = obterVisualizacao(lstRetorno);
        }
        catch (Exception e)
        {
            throw new CDException(e.getMessage(), e);
        }
        finally
        {
            DAO.desconectar(obterNomeConexao());
        }
        return lstRetorno;
    }

    /**
     * Obtém lista da consulta de acordo com parâmetros passados
     * 
     * @param objFuncionalidadeSistema objeto contendo os parâmetros da consulta
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public List obterPelaConsulta(FuncionalidadeSistema objFuncionalidadeSistema) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém os registros da página
        FuncionalidadeSistemaDAO objFuncionalidadeSistemaDAO = new FuncionalidadeSistemaDAO(obterNomeConexao());
        List lstRetorno = null;
        try
        {
            lstRetorno = objFuncionalidadeSistemaDAO.obterPelaConsulta(objFuncionalidadeSistema);
            lstRetorno = obterVisualizacao(lstRetorno);
        }
        catch (Exception e)
        {
            throw new CDException(e.getMessage(), e);
        }
        finally
        {
            DAO.desconectar(obterNomeConexao());
        }
        return lstRetorno;
    }

    /**
     * Obtém objeto de visualização
     * 
     * @param lstFuncionalidadeSistema objeto contendo as funcionalidades a serem copiadas
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    private List obterVisualizacao(List lstFuncionalidadeSistema)
    {
        List lstRetorno = new ArrayList();
        for (int i = 0; i < lstFuncionalidadeSistema.size(); i++)
        {
            FuncionalidadeSistema objFuncionalidade = (FuncionalidadeSistema) lstFuncionalidadeSistema.get(i);
            FuncionalidadeSistemaVisualizacao objFuncionalidadeSistemaVisualizacao = new FuncionalidadeSistemaVisualizacao();
            Copia.criar(objFuncionalidade, objFuncionalidadeSistemaVisualizacao);
            objFuncionalidadeSistemaVisualizacao.setRetorno(objFuncionalidade.getIdentificador().intValue() + ";" + objFuncionalidade.getDescricao());
            lstRetorno.add(objFuncionalidadeSistemaVisualizacao);
        }
        return lstRetorno;
    }

    /**
     * 
     * Realiza importação das funcionalidades a partir de um arquivo XML
     * 
     * @param ipsArquivo Arquivo XML contendo os dados das funcionalidades a serem importadas
     * @param objUsuarioSistema Pojo contendo os dados do usuário a ser validado como administrador
     * @param lstErros Lista contendo as funcionalidades que deram erro durante a importação
     * @param lstInclusoes Lista contendo as funcionalidades que foram incluídas durante a  importação
     * @param lstExistentes Lista contendo as funcionalidades que já existem.
     * 
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public void importar(InputStream ipsArquivo, Sistema objSistema, UsuarioSistema objUsuarioSistema, List lstErros, List lstInclusoes, List lstExistentes) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        GestorSistemaFacade objGestorSistemaFacade = new GestorSistemaFacade(obterNomeConexao());
        try
        {
            // Verifica se o usuário é administrador do sistema
            if (!objGestorSistemaFacade.isAdministradorSistema(objSistema, objUsuarioSistema))
            {
                throw new CDException("Somente administradores deste sistema podem importar Funcionalidades");
            }

            //  Obtém XML escolhido
            SAXBuilder builder = new SAXBuilder();
            Document doc = builder.build(ipsArquivo);

            // Recupera o primeiro nível do XML
            Element root = doc.getRootElement();

            DAO.iniciarTransacao(obterNomeConexao());

            FuncionalidadeSistema objFuncionalidadeModulo = obterFuncionalidadeModulo(root);

            gravarFuncionalidades(root.getChildren("mapeamentos"), objFuncionalidadeModulo, objSistema, objFuncionalidadeModulo, lstErros, lstInclusoes, lstExistentes);

            if (lstErros.size() > 0)
            {
                DAO.desfazerTransacao(obterNomeConexao());
            }
            else
            {
                DAO.realizarTransacao(obterNomeConexao());
            }
        }
        catch (CDException cde)
        {
            DAO.desfazerTransacao(obterNomeConexao());
            throw cde;
        }
        catch (IOException e)
        {
            DAO.desfazerTransacao(obterNomeConexao());
            throw new CDException("Erro ao processar arquivo XML");
        }
        catch (JDOMException e)
        {
            DAO.desfazerTransacao(obterNomeConexao());
            throw new CDException("Erro ao processar arquivo XML");
        }
        finally
        {
            DAO.desconectar(obterNomeConexao());
        }
    }

    /**
     * 
     * Grava as funcionalidades importadas do arquivo XML
     * 
     * @param lstFilhos Lista contendo os filhos de um determinado nó da arvore
     * @param objFuncionalidadeAgrupadora Funcionalidade pai/superior da lista de filhos
     * @param objSistema POJO contendo o sistema das funcionalidades
     * @param objFuncionalidadeModulo Funcionalidade módulo da importação
     * @param lstErros Lista contendo as funcionalidades que deram erro durante a importação
     * @param lstInclusoes Lista contendo as funcionalidades que foram incluídas durante a  importação
     * @param lstExistentes Lista contendo as funcionalidades que já existem.
     * 
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    private void gravarFuncionalidades(List lstFilhos, FuncionalidadeSistema objFuncAgrupadora, Sistema objSistema, FuncionalidadeSistema objFuncionalidadeModulo, List lstErros, List lstInclusoes, List lstExistentes) throws CDException
    {
        if (lstFilhos != null)
        {
            try
            {
                FuncionalidadeSistemaDAO objFuncionalidadeSistemaDAO = new FuncionalidadeSistemaDAO(obterNomeConexao());
                for (int i = 0; i < lstFilhos.size(); i++)
                {
                    FuncionalidadeSistema objFunc = new FuncionalidadeSistema();
                    Element mapeamento = (Element) lstFilhos.get(i);
                    if (mapeamento.getName().equals("mapeamentos"))
                    {
                        gravarFuncionalidades(mapeamento.getChildren("mapeamento"), objFuncAgrupadora, objSistema, objFuncionalidadeModulo, lstErros, lstInclusoes, lstExistentes);
                        break;
                    }

                    objFunc.setNome(!"".equals(mapeamento.getAttributeValue("nome")) ? mapeamento.getAttributeValue("nome") : mapeamento.getAttributeValue("objetoControlado"));
                    objFunc.setDescricao(!"".equals(mapeamento.getAttributeValue("descricao")) ? mapeamento.getAttributeValue("descricao") : mapeamento.getAttributeValue("objetoControlado"));
                    objFunc.setObjetoControlado(mapeamento.getAttributeValue("objetoControlado"));
                    objFunc.setFuncionalidadeAgrupadora(objFuncAgrupadora);
                    objFunc.setSistema(objFuncionalidadeModulo.getSistema());

                    // Inclui, caso o objeto controlado ainda não exista
                    FuncionalidadeSistema objFuncionalidadeExistente = objFuncionalidadeSistemaDAO.obterPeloObjetoControlado(mapeamento.getAttributeValue("objetoControlado"));
                    if (objFuncionalidadeExistente == null && objFunc.getObjetoControlado() != null)
                    {
                        objFuncionalidadeSistemaDAO.incluir(objFunc);
                        lstInclusoes.add(objFunc);
                    }
                    else
                    {
                        FuncionalidadeSistema objFuncionalidadeCadastrada = obterModulo(objFuncionalidadeExistente, objFuncionalidadeModulo);
                        if ((objFuncionalidadeCadastrada != null && objFuncionalidadeModulo.getIdentificador().intValue() == objFuncionalidadeCadastrada.getIdentificador().intValue())
                                || objFuncionalidadeCadastrada == null)
                        {
                            lstExistentes.add(objFuncionalidadeExistente);
                        }
                        else
                        {
                            objFuncionalidadeExistente.setDescricao(objFuncionalidadeCadastrada.getNome());
                            objFuncionalidadeSistemaDAO.inicializarSistema(objFuncionalidadeExistente);
                            lstErros.add(objFuncionalidadeExistente);
                        }
                        objFunc = objFuncionalidadeExistente;
                    }
                    gravarFuncionalidades(mapeamento.getChildren("mapeamento"), objFunc, objSistema, objFuncionalidadeModulo, lstErros, lstInclusoes, lstExistentes);
                }
            }
            catch (CDException cde)
            {
                DAO.desfazerTransacao(obterNomeConexao());
                throw cde;
            }
            finally
            {
                DAO.desconectar(obterNomeConexao());
            }
        }
    }

    /**
     * 
     * Obtém a funcionalidade módulo de acordo com os parâmetros
     * 
     * @param objFuncionalidadeExistente POJO contendo os dados da funcionalidade existente
     * @param objFuncionalidadeModulo POJO contendo os dados da funcionalidade módulo
     *
     * @return FuncionalidadeSistema POJO contendo os dados obtidos na consulta
     * 
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    private FuncionalidadeSistema obterModulo(FuncionalidadeSistema objFuncionalidadeExistente, FuncionalidadeSistema objFuncionalidadeModulo) throws CDException
    {
        FuncionalidadeSistema objRetorno = null;
        try
        {
            FuncionalidadeSistemaDAO objFuncionalidadeSistemaDAO = new FuncionalidadeSistemaDAO(obterNomeConexao());

            if (objFuncionalidadeExistente != null)
            {
                while (objFuncionalidadeExistente.getFuncionalidadeAgrupadora() != null)
                {
                    objFuncionalidadeExistente = (FuncionalidadeSistema) objFuncionalidadeSistemaDAO.obterPelaChave(objFuncionalidadeExistente.getFuncionalidadeAgrupadora());
                }
                objRetorno = objFuncionalidadeExistente;
            }
        }
        catch (CDException cde)
        {
            throw cde;
        }
        finally
        {
            DAO.desconectar(obterNomeConexao());
        }

        return objRetorno;
    }

    /**
     * 
     * Obtém a funcionalidade módulo de acordo com parametro
     * 
     * @param root elemento do XML que contém a descrição do módulo a ser consultado
     *
     * @return FuncionalidadeSistema POJO contendo os dados obtidos na consulta
     * 
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    private FuncionalidadeSistema obterFuncionalidadeModulo(Element root) throws CDException
    {
        // Declarações
        FuncionalidadeSistema objFuncionalidadeSistema = new FuncionalidadeSistema();

        // Instancia DAO e obtém os registros da página
        FuncionalidadeSistemaDAO objFuncionalidadeSistemaDAO = new FuncionalidadeSistemaDAO(obterNomeConexao());
        try
        {
            String strModulo = root.getChild("modulo").getAttributeValue("objetoControlado");

            objFuncionalidadeSistema = objFuncionalidadeSistemaDAO.obterPeloObjetoControladoAgrupadorNull(strModulo);
            if (objFuncionalidadeSistema == null)
            {
                throw new CDException("Não existe uma funcionalidade com o nome do módulo para a devida associação");
            }
            objFuncionalidadeSistemaDAO.inicializarSistema(objFuncionalidadeSistema);
        }
        catch (CDException cde)
        {
            throw cde;
        }
        finally
        {
            DAO.desconectar(obterNomeConexao());
        }
        return objFuncionalidadeSistema;

    }

    /**
     * 
     * Adiciona um encaminhamendo ao XML
     * 
     * @param strNome String que contém o valor a ser atribuído ao nome
     * @param strDescricao String que contém o valor a ser atribuído ao descrição
     * @param strObjetoControlado String que contém o valor a ser atribuído ao objetoControlado
     * 
     */
    public void adicionarEncaminhamento(String strNome, String strDescricao, String strObjetoControlado)
    {
        lstEncaminhamento.add(new String[] { strNome, strDescricao, strObjetoControlado });
    }

    /**
     * 
     * Adiciona uma ação ao XML
     * 
     * @param strNome String que contém o valor a ser atribuído ao nome
     * @param strDescricao String que contém o valor a ser atribuído ao descrição
     * @param strObjetoControlado String que contém o valor a ser atribuído ao objetoControlado
     * 
     */
    public void adicionarAcao(String strNome, String strDescricao, String strObjetoControlado)
    {
        lstAcao.add(new String[] { strNome, strDescricao, strObjetoControlado });
    }

    /**
     * 
     * Cria XML a partir de uma funcionalidade e sua hierarquia
     * 
     * @param objFuncionalidadeSistema POJO representando o objeto a ser obtido
     * 
     * @return Document Objeto XML obtido pela consulta
     * 
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public Document obterDocumentoXML(FuncionalidadeSistema objFuncionalidadeSistema) throws CDException
    {
        Document docXML = null;
        try
        {

            // cria um elemento 
            Element sistema = new Element("sistema");

            //cria uma instância no documento
            docXML = new Document(sistema);

            FuncionalidadeSistema objFunc = obterPelaChave(objFuncionalidadeSistema);
            Element objElementEncaminhamento = adicionarMapeamento(objFunc, null, ELEMENTO_SUPERIOR);

            // Obtém encaminhamentos inferiores
            List lstFilhos = obterPelaFuncionalidadeAgrupadora(objFunc.getIdentificador());

            obterFilhos(lstFilhos, objElementEncaminhamento);

            // Obtém os encaminhamentos superiores
            if (objFunc.getFuncionalidadeAgrupadora() != null)
            {
                do
                {
                    objFunc = obterPelaChave(objFunc.getFuncionalidadeAgrupadora());
                    if (objFunc.getFuncionalidadeAgrupadora() != null)
                    {
                        objElementEncaminhamento = adicionarMapeamento(objFunc, objElementEncaminhamento, ELEMENTO_SUPERIOR);
                    }
                }
                while (objFunc.getFuncionalidadeAgrupadora() != null);
            }

            // cria a aplicação
            Element objElementAplicacao = new Element("aplicacao");
            objElementAplicacao.setAttribute(new Attribute("nome", objFuncionalidadeSistema.getSistema().getDescricao()));
            sistema.addContent(objElementAplicacao);

            // cria o módulo
            Element objElementModulo = new Element("modulo");
            objElementModulo.setAttribute(new Attribute("nome", objFunc.getNome()));
            objElementModulo.setAttribute(new Attribute("descricao", objFunc.getDescricao()));
            objElementModulo.setAttribute(new Attribute("objetoControlado", objFunc.getObjetoControlado()));
            sistema.addContent(objElementModulo);

            Element objElementMapeamentos = new Element("mapeamentos");
            objElementMapeamentos.addContent(objElementEncaminhamento);

            sistema.addContent(objElementMapeamentos);

        }
        catch (Exception e)
        {
            throw new CDException(e.getMessage(), e);
        }
        finally
        {
            DAO.desconectar(obterNomeConexao());
        }
        return docXML;
    }

    /**
     * 
     * Obtém os filhos de um elemento no XML 
     * 
     * @param lstFilhos Obtém os filhos de uma lista de funcionalidades recursivamente
     * @param objElement Elemento do XML
     * 
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    private void obterFilhos(List lstFilhos, Element objElement) throws CDException
    {
        if (lstFilhos != null)
        {
            for (int i = 0; i < lstFilhos.size(); i++)
            {
                FuncionalidadeSistema objFuncionalidadeSistema = (FuncionalidadeSistema) lstFilhos.get(i);
                Element objElementFilha = adicionarMapeamento(objFuncionalidadeSistema, objElement, ELEMENTO_INFERIOR);
                List lstNetos = obterPelaFuncionalidadeAgrupadora(objFuncionalidadeSistema.getIdentificador());
                obterFilhos(lstNetos, objElementFilha);
            }
        }
    }

    /**
     * 
     * Adiciona um elemento ao XML
     * 
     * @param lstFilhos Obtém os filhos de uma lista de funcionalidades recursivamente
     * @param objElement Elemento do XML
     * 
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    private Element adicionarMapeamento(FuncionalidadeSistema objFuncionalidadeSistema, Element objElement, int intTipoAdicao)
    {
        Element objRetorno = new Element("mapeamento");
        objRetorno.setAttribute(new Attribute("nome", objFuncionalidadeSistema.getNome()));
        objRetorno.setAttribute(new Attribute("descricao", objFuncionalidadeSistema.getDescricao()));
        objRetorno.setAttribute(new Attribute("objetoControlado", objFuncionalidadeSistema.getObjetoControlado()));
        if (objElement != null)
        {
            if (intTipoAdicao == ELEMENTO_SUPERIOR)
            {
                objRetorno.addContent(objElement);
            }
            else if (intTipoAdicao == ELEMENTO_INFERIOR)
            {
                objElement.addContent(objRetorno);
            }
        }

        return objRetorno;
    }

}
