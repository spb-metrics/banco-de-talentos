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

    // Variáveis de instância
    private static Log log = LogFactory.getLog(ConsultaEstatisticaCadastroFacade.class);

    /**
     * Efetua consulta de estatísticas
     * 
     * @param strDataInicio Data de início da consulta
     * @param strDataTermino Data de término da consulta
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
            // Obtém grupos habilitados ao usuário logado consultar
            String strGrupos = "";
            if (blnFiltroGrupo)
            {
                strGrupos = objFiltroConsultaDAO.obterGruposHabilitadosConsultaCurriculo(strIdentificadorUsuario);
            }
            // Obtém lista de talentos de acordo com o filtro
            List lstTalentos = objTalentoDAO.realizarConsultaEstatistica(strDataInicio, strDataTermino, lstQuebras, strGrupos);

            // Inicializa objetos lazy
            objTalentoDAO.inicializarCategoriaTalento(lstTalentos);
            objTalentoDAO.inicializarPessoa(lstTalentos);

            // Identação realizada de acordo com a quebra (só para 3 níveis)
            String[] strIdentacao = { "", "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;", "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" };

            // Coleção que armazena os objetos Estatistica, apenas para auxiliar a montagem da lista de resultado.
            Map mapEstatistica = new HashMap();

            // Coleção que armazena as pessoas, apenas para obter a quantidade total de pessoas.
            Map mapQtdPessoas = new HashMap();

            // Lista contendo o resultado da consulta
            lstResultado = new ArrayList();

            // Descrição da Quebra
            String strDescricao = "";

            // Caminho completo da quebra
            String strCaminho = "";

            // Contém os parametros que seram usados posteriormente para listagem das pessoas que pertencem a quebra.
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

                // Inicializa variáveis.
                strCaminho = "";
                strParametro = "&dtIni=" + strDataInicio + "&dtFim=" + strDataTermino;

                // Monta o resultado de acordo com a quebra selecionada
                for (int j = 0; j < lstQuebras.size(); j++)
                {
                    // Monta a descrição e o parametro de cada quebra

                    // Verifica se a quebra é por grupo
                    if (((String) lstQuebras.get(j)).equalsIgnoreCase("grupo"))
                    {
                        strDescricao = objTalento.getPessoa().getGrupo().getDescricao();
                        strParametro += "&grupo=" + objTalento.getPessoa().getGrupo().getCodigo();
                    }
                    // Verifica se a quebra é por categoria
                    else if (((String) lstQuebras.get(j)).equalsIgnoreCase("categoria"))
                    {
                        strDescricao = objTalento.getCategoriaTalento().getNome();
                        strParametro += "&categoria=" + objTalento.getCategoriaTalento().getIdentificador();
                    }
                    // Verifica se a quebra é por data
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
                        // Instancia objeto estatística e atribui valores
                        objEstatistica = new Estatistica();
                        objEstatistica.setDescricao(strIdentacao[j] + strDescricao);
                        objEstatistica.setQuantidade(1);
                        objEstatistica.setPessoa(objTalento.getPessoa().getIdentificador().intValue());
                        objEstatistica.setNivel(j);
                        objEstatistica.setParametro(strParametro);
                        mapEstatistica.put(strCaminho, objEstatistica);
                        // Put que serve para identificação posterior da inclusão do servidor no caminho indicado
                        mapEstatistica.put(intIdentificadorPessoa.intValue() + strCaminho, objEstatistica);

                        // Adiciona objeto de estatistica na lista de resultado da consulta
                        lstResultado.add(objEstatistica);
                    }
                    else
                    {
                        // Identifica se a pessoa já foi incluído no caminho especificado
                        objEstatistica = (Estatistica) mapEstatistica.get(intIdentificadorPessoa.intValue() + strCaminho);
                        // Caso a pessoa não tenha sido inclusa, a quantidade é incrementada
                        if (objEstatistica == null)
                        {
                            objEstatistica = (Estatistica) mapEstatistica.get(strCaminho);
                            objEstatistica.setQuantidade(objEstatistica.getQuantidade() + 1);
                            // Put que serve para identificação posterior da inclusão do servidor no caminho indicado
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
     * Repassa o pedido de obtenção do número total de registros da consulta
     *
     * @param strDataInicial Data de início da consulta
     * @param strDataTermino Data de término da consulta
     * @param strGrupo       Grupo a ser consultado 
     * @param strCategoria   Categoria a ser consultada
     * @param strData        Data a ser consultada
     *
     * @return int Número que representa o número de registros que satisfazem os criterios
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
            // Obtém grupos habilitados ao usuário logado consultar
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
     * Repassa o pedido de obtenção da listagem da consulta
     *
     * @param strDataInicial Data de início da consulta
     * @param strDataTermino Data de término da consulta
     * @param strGrupo       Grupo a ser consultado 
     * @param strCategoria   Categoria a ser consultada
     * @param strData        Data a ser consultada
     * @param intPagina      Pagina que deverá ser retornada
     * @param intMaximoPagina Máximo de registro que serão retornados
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
            // Obtém grupos habilitados ao usuário logado consultar
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
