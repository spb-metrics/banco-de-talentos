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

package br.gov.camara.negocio.bancotalentos.facade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.gov.camara.exception.CDException;
import br.gov.camara.negocio.DAO;
import br.gov.camara.negocio.Facade;
import br.gov.camara.negocio.bancotalentos.dao.FiltroConsultaDAO;
import br.gov.camara.negocio.bancotalentos.dao.TalentoDAO;
import br.gov.camara.negocio.bancotalentos.pojo.Estatistica;
import br.gov.camara.negocio.bancotalentos.pojo.Talento;

/**
 * Facade para a consulta de talentos
 */
public class ConsultaEstatisticaCadastroFacade extends Facade
{

    // Vari�veis de inst�ncia
    private static Log log = LogFactory.getLog(ConsultaEstatisticaCadastroFacade.class);

    /**
     * Efetua consulta de estat�sticas
     * 
     * @param strDataInicio Data de in�cio da consulta
     * @param strDataTermino Data de t�rmino da consulta
     * @param lstQuebras Quebras selecionadas para consulta
     * 
     * @return ArrayList Contendo o resultado da consulta
     * 
     * @throws CDException se ocorrer um erro relacionado com banco de dados 
     */
    public List consultar(String strIdentificadorUsuario, boolean blnFiltroGrupo, String strDataInicio, String strDataTermino, List lstQuebras) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }
        // Instancia DAO
        TalentoDAO objTalentoDAO = new TalentoDAO();
        FiltroConsultaDAO objFiltroConsultaDAO = new FiltroConsultaDAO();
        List lstResultado = null;

        try
        {
            // Obt�m grupos habilitados ao usu�rio logado consultar
            String strGrupos = "";
            if (blnFiltroGrupo)
            {
                strGrupos = objFiltroConsultaDAO.obterGruposHabilitadosConsultaCurriculo(strIdentificadorUsuario);
            }
            // Obt�m lista de talentos de acordo com o filtro
            List lstTalentos = objTalentoDAO.realizarConsultaEstatistica(strDataInicio, strDataTermino, lstQuebras, strGrupos);

            // Inicializa objetos lazy
            objTalentoDAO.inicializarCategoriaTalento(lstTalentos);
            objTalentoDAO.inicializarPessoa(lstTalentos);

            // Identa��o realizada de acordo com a quebra (s� para 3 n�veis)
            String[] strIdentacao = { "", "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;", "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" };

            // Cole��o que armazena os objetos Estatistica, apenas para auxiliar a montagem da lista de resultado.
            Map mapEstatistica = new HashMap();

            // Cole��o que armazena as pessoas, apenas para obter a quantidade total de pessoas.
            Map mapQtdPessoas = new HashMap();

            // Lista contendo o resultado da consulta
            lstResultado = new ArrayList();

            // Descri��o da Quebra
            String strDescricao = "";

            // Caminho completo da quebra
            String strCaminho = "";

            // Cont�m os parametros que seram usados posteriormente para listagem das pessoas que pertencem a quebra.
            String strParametro = "";

            Talento objTalento = null;

            for (int i = 0; i < lstTalentos.size(); i++)
            {
                // Recupera objeto.
                objTalento = (Talento) lstTalentos.get(i);

                // Recupera identificador da pessoa
                Integer intIdentificadorPessoa = objTalento.getPessoa().getIdentificador();

                if (mapQtdPessoas.get(intIdentificadorPessoa) == null)
                {
                    mapQtdPessoas.put(intIdentificadorPessoa, intIdentificadorPessoa);
                }

                // Inicializa vari�veis.
                strCaminho = "";
                strParametro = "&dtIni=" + strDataInicio + "&dtFim=" + strDataTermino;

                // Monta o resultado de acordo com a quebra selecionada
                for (int j = 0; j < lstQuebras.size(); j++)
                {
                    // Monta a descri��o e o parametro de cada quebra

                    // Verifica se a quebra � por grupo
                    if (((String) lstQuebras.get(j)).equalsIgnoreCase("grupo"))
                    {
                        strDescricao = objTalento.getPessoa().getGrupo().getDescricao();
                        strParametro += "&grupo=" + objTalento.getPessoa().getGrupo().getCodigo();
                    }
                    // Verifica se a quebra � por categoria
                    else if (((String) lstQuebras.get(j)).equalsIgnoreCase("categoria"))
                    {
                        strDescricao = objTalento.getCategoriaTalento().getNome();
                        strParametro += "&categoria=" + objTalento.getCategoriaTalento().getIdentificador();
                    }
                    // Verifica se a quebra � por data
                    else if (((String) lstQuebras.get(j)).equalsIgnoreCase("data"))
                    {
                        strDescricao = objTalento.getDataLancamentoFormatada();
                        strParametro += "&data=" + objTalento.getDataLancamentoFormatada();
                    }

                    // Caminho da quebra
                    strCaminho += "/" + strDescricao;

                    Estatistica objEstatistica = null;
                    if (mapEstatistica.get(strCaminho) == null)
                    {
                        // Instancia objeto estat�stica e atribui valores
                        objEstatistica = new Estatistica();
                        objEstatistica.setDescricao(strIdentacao[j] + strDescricao);
                        objEstatistica.setQuantidade(1);
                        objEstatistica.setPessoa(objTalento.getPessoa().getIdentificador().intValue());
                        objEstatistica.setNivel(j);
                        objEstatistica.setParametro(strParametro);
                        mapEstatistica.put(strCaminho, objEstatistica);
                        // Put que serve para identifica��o posterior da inclus�o do servidor no caminho indicado
                        mapEstatistica.put(intIdentificadorPessoa.intValue() + strCaminho, objEstatistica);

                        // Adiciona objeto de estatistica na lista de resultado da consulta
                        lstResultado.add(objEstatistica);
                    }
                    else
                    {
                        // Identifica se a pessoa j� foi inclu�do no caminho especificado
                        objEstatistica = (Estatistica) mapEstatistica.get(intIdentificadorPessoa.intValue() + strCaminho);
                        // Caso a pessoa n�o tenha sido inclusa, a quantidade � incrementada
                        if (objEstatistica == null)
                        {
                            objEstatistica = (Estatistica) mapEstatistica.get(strCaminho);
                            objEstatistica.setQuantidade(objEstatistica.getQuantidade() + 1);
                            // Put que serve para identifica��o posterior da inclus�o do servidor no caminho indicado
                            mapEstatistica.put(intIdentificadorPessoa.intValue() + strCaminho, objEstatistica);
                        }
                    }
                }
            }
            // Inclui o total de pessoas no geral        	
            Estatistica objEstatistica = new Estatistica();
            objEstatistica.setDescricao("<b>Total de pessoas no intervalo<b>");
            objEstatistica.setNivel(1);
            objEstatistica.setQuantidade(mapQtdPessoas.size());
            objEstatistica.setParametro("&dtIni=" + strDataInicio + "&dtFim=" + strDataTermino);
            lstResultado.add(objEstatistica);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }

        return lstResultado;
    }

    /**
     * Repassa o pedido de obten��o do n�mero total de registros da consulta
     *
     * @param strDataInicial Data de in�cio da consulta
     * @param strDataTermino Data de t�rmino da consulta
     * @param strGrupo       Grupo a ser consultado 
     * @param strCategoria   Categoria a ser consultada
     * @param strData        Data a ser consultada
     *
     * @return int N�mero que representa o n�mero de registros que satisfazem os criterios
     *
     * @throws CDException Se ocorrer algum erro relacionado com o acesso a banco de dados
     *
     */
    public int obterTotalRegistros(String strIdentificadorUsuario, boolean blnFiltroGrupo, String strDataInicial, String strDataTermino, String strGrupo, String strCategoria, String strData) throws CDException
    {
        // Instancia DAO/Bean
        TalentoDAO objTalentoDAO = new TalentoDAO();
        FiltroConsultaDAO objFiltroConsultaDAO = new FiltroConsultaDAO();

        int retorno = 0;

        try
        {
            // Obt�m grupos habilitados ao usu�rio logado consultar
            String strGrupos = "";
            if (blnFiltroGrupo)
            {
                strGrupos = objFiltroConsultaDAO.obterGruposHabilitadosConsultaCurriculo(strIdentificadorUsuario);
            }
            retorno = objTalentoDAO.obterTotalListagemConsulta(strDataInicial, strDataTermino, strGrupo, strCategoria, strData, strGrupos);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
        return retorno;
    }

    /**
     * Repassa o pedido de obten��o da listagem da consulta
     *
     * @param strDataInicial Data de in�cio da consulta
     * @param strDataTermino Data de t�rmino da consulta
     * @param strGrupo       Grupo a ser consultado 
     * @param strCategoria   Categoria a ser consultada
     * @param strData        Data a ser consultada
     * @param intPagina      Pagina que dever� ser retornada
     * @param intMaximoPagina M�ximo de registro que ser�o retornados
     *
     * @return List Lista contento o resultado da consulta
     *
     * @throws CDException Se ocorrer algum erro relacionado com o acesso a banco de dados
     *
     */
    public List consultarListagem(String strIdentificadorUsuario, boolean blnFiltroGrupo, String strDataInicial, String strDataTermino, String strGrupo, String strCategoria, String strData, int intPagina, int intMaximoPagina) throws CDException
    {

        // Instancia DAO/Bean
        TalentoDAO objTalentoDAO = new TalentoDAO();
        FiltroConsultaDAO objFiltroConsultaDAO = new FiltroConsultaDAO();

        List lstTalentos = null;
        List lstRetorno = new ArrayList();
        Map mapTalentos = new HashMap();
        try
        {
            // Obt�m grupos habilitados ao usu�rio logado consultar
            String strGrupos = "";
            if (blnFiltroGrupo)
            {
                strGrupos = objFiltroConsultaDAO.obterGruposHabilitadosConsultaCurriculo(strIdentificadorUsuario);
            }

            lstTalentos = objTalentoDAO.obterListagemConsulta(strDataInicial, strDataTermino, strGrupo, strCategoria, strData, strGrupos);
            for (int i = 0; i < lstTalentos.size(); i++)
            {
                Talento objTalento = (Talento) lstTalentos.get(i);
                if (mapTalentos.get(objTalento.getPessoa().getIdentificador()) == null)
                {
                    lstRetorno.add(objTalento);
                    mapTalentos.put(objTalento.getPessoa().getIdentificador(), objTalento);
                }
            }

            //Inicializa objeto pessoa
            objTalentoDAO.inicializarPessoa(lstRetorno);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
        return lstRetorno;
    }
}
