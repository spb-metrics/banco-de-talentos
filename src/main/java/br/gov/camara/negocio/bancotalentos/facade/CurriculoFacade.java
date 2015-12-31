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
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.gov.camara.biblioteca.util.GenericComparator;
import br.gov.camara.exception.CDException;
import br.gov.camara.negocio.DAO;
import br.gov.camara.negocio.Facade;
import br.gov.camara.negocio.bancotalentos.dao.AtributoTalentoValoradoDAO;
import br.gov.camara.negocio.bancotalentos.dao.CategoriaAtributoTalentoDAO;
import br.gov.camara.negocio.bancotalentos.dao.PessoaDAO;
import br.gov.camara.negocio.bancotalentos.dao.TalentoDAO;
import br.gov.camara.negocio.bancotalentos.dto.CurriculoRelatorioDTO;
import br.gov.camara.negocio.bancotalentos.dto.TalentoRelatorioDTO;
import br.gov.camara.negocio.bancotalentos.pojo.AtributoTalentoValorado;
import br.gov.camara.negocio.bancotalentos.pojo.Pessoa;
import br.gov.camara.negocio.bancotalentos.pojo.Talento;
import br.gov.camara.negocio.bancotalentos.util.GerenciadorAtributoVirtual;

/**
 * Facade para a exibi��o do curr�culo de determinada pessoa
 * 
 */
public class CurriculoFacade extends Facade
{
    // Vari�veis de inst�ncia
    private static Log log = LogFactory.getLog(CurriculoFacade.class);

    /**
     * Obt�m a pessoa titular do curr�culo pela chave
     *
     * @param strChave Chave do registro a ser obtido
     * @return Pessoa POJO representando o registro obtido
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public Pessoa obterPessoaPelaChave(String strChave) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m os registros
        PessoaDAO objPessoaDAO = new PessoaDAO();
        Pessoa objPessoa = null;
        try
        {
            objPessoa = (Pessoa) objPessoaDAO.obterPelaChave(strChave);
            if (objPessoa != null)
            {
                objPessoaDAO.inicializarGrupo(objPessoa);
                objPessoaDAO.inicializarLotacoes(objPessoa);
            }
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
        return objPessoa;
    }

    /**
     * Obt�m talentos da pessoa 
     *
     * @param objPessoa Pessoa detentora dos talentos
     * @return List Lista com os POJOs representando o registros obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public List obterTalentosPorPessoa(Pessoa objPessoa) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m os registros
        TalentoDAO objTalentoDAO = new TalentoDAO();
        List lstTalentos = null;
        try
        {
            lstTalentos = objTalentoDAO.obterPorPessoa(objPessoa);
            objTalentoDAO.inicializarCategoriaTalento(lstTalentos);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
        return lstTalentos;
    }

    /**
     * Obt�m valora��es de determinado talento 
     *
     * @param objTalento Talento a ser consultado
     * @return List Lista com os POJOs representando o registros obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public List obterValoracoesPorTalento(Talento objTalento) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m os registros
        AtributoTalentoValoradoDAO objAtributoTalentoValoradoDAO = new AtributoTalentoValoradoDAO();
        CategoriaAtributoTalentoDAO objCategoriaAtributoTalentoDAO = new CategoriaAtributoTalentoDAO();
        List lstAtributosTalentoValorados = null;
        try
        {
            //Para uma dada categoria, obtem todos os valores que o usu�rio preencheu
            lstAtributosTalentoValorados = objAtributoTalentoValoradoDAO.obterPeloTalento(objTalento);
            objAtributoTalentoValoradoDAO.inicializarCategoriaAtributoTalento(lstAtributosTalentoValorados);
            ListIterator itrAtributosTalentoValorados = lstAtributosTalentoValorados.listIterator();
            while (itrAtributosTalentoValorados.hasNext())
            {
                //Varre todos os AtributoTalentoValorado, ou seja os valores preenchidos, para fazer as inicializacoes necessarias (lazy)
                AtributoTalentoValorado objAtributoTalentoValorado = (AtributoTalentoValorado) itrAtributosTalentoValorados.next();
                objCategoriaAtributoTalentoDAO.inicializarAtributoTalento(objAtributoTalentoValorado.getCategoriaAtributoTalento());

                //Trata atributo virtual
                //Pode ser que exista mais de uma informa��o para ser apresentada e aqui � feito o desmembramento
                if (GerenciadorAtributoVirtual.isAtributoVirtual(objAtributoTalentoValorado.getCategoriaAtributoTalento().getAtributoTalento().getNome()))
                {
                    String aux[] = objAtributoTalentoValorado.getValoracao().split("\\*\\|\\*");
                    int count = 1;
                    for (int i = 0; i < aux.length; i++)
                    {
                        if (i == 0)//ignora o primeiro valor pois este j� esta na lstAtributosTalentoValorados
                        {
                            objAtributoTalentoValorado.setValoracao(aux[i]);
                            continue;
                        }

                        AtributoTalentoValorado atributo = new AtributoTalentoValorado();
                        atributo.setIdentificador(new Integer(count++));//� preciso associar um id diferente para cada atributo para que gere corretamente o relatorio PDF
                        atributo.setValoracao(aux[i]);
                        atributo.setCategoriaAtributoTalento(objAtributoTalentoValorado.getCategoriaAtributoTalento());
                        atributo.setTalento(objTalento);
                        atributo.setAtributoTalentoOpcao(objAtributoTalentoValorado.getAtributoTalentoOpcao());
                        //Toda a lista ser� movida para a direita
                        itrAtributosTalentoValorados.add(atributo);

                    }
                    //Insere um atributo apenas para pular de linha
                    AtributoTalentoValorado atributo = new AtributoTalentoValorado();
                    atributo.setIdentificador(new Integer(count++));//� preciso associar um id diferente para cada atributo para que gere corretamente o relatorio PDF
                    atributo.setValoracao("");
                    atributo.setCategoriaAtributoTalento(objAtributoTalentoValorado.getCategoriaAtributoTalento());
                    atributo.setTalento(objTalento);
                    atributo.setAtributoTalentoOpcao(objAtributoTalentoValorado.getAtributoTalentoOpcao());
                    //Toda a lista ser� movida para a direita
                    itrAtributosTalentoValorados.add(atributo);

                }
            }
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
        return lstAtributosTalentoValorados;
    }

    /**
     * Obt�m os curriculos
     *
     * @param String[] Lista de pessoas que ser�o processadas os curriculos 
     * @return List Lista com os POJOs representando os curriculos
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public List obterCurriculos(String[] strIdePessoa) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        List lstRetorno = new ArrayList();

        try
        {
            for (int i = 0; i < strIdePessoa.length; i++)
            {
                Pessoa objPessoa = obterPessoaPelaChave(strIdePessoa[i]);
                if (objPessoa != null)
                {
                    CurriculoRelatorioDTO objCurriculoRelatorioDTO = new CurriculoRelatorioDTO();
                    objCurriculoRelatorioDTO.setPessoa(objPessoa);

                    // Obt�m Lota��es
                    List lstLotacoes = new ArrayList(objPessoa.getLotacoes());
                    GenericComparator objGenericComparator = new GenericComparator("calDataInicio", GenericComparator.DESC, null, "getTime");
                    Collections.sort(lstLotacoes, objGenericComparator);
                    objCurriculoRelatorioDTO.setLotacoes(lstLotacoes);

                    // Obt�m talentos da pessoa
                    List lstTalentos = obterTalentosPorPessoa(objPessoa);

                    // Obt�m valora��es
                    Iterator itrTalentos = lstTalentos.iterator();
                    List lstTalentoRelatorioDTO = new ArrayList();
                    TalentoRelatorioDTO objTalentoRelatorioDTO = null;

                    while (itrTalentos.hasNext())
                    {
                        Talento objTalento = (Talento) itrTalentos.next();

                        // Obt�m a lista de atributo de talentos
                        List lstAtributosTalentoValorados = obterValoracoesPorTalento(objTalento);

                        int intIdCategoriaAtributoAnterior = 0;
                        for (int j = 0; j < lstAtributosTalentoValorados.size(); j++)
                        {
                            objTalentoRelatorioDTO = new TalentoRelatorioDTO();
                            // Seta data de lan�amento do talento
                            objTalentoRelatorioDTO.setDataLancamento(objTalento.getDataLancamentoFormatada());

                            // Seta o nome do talento
                            objTalentoRelatorioDTO.setNomeTalento(objTalento.getCategoriaTalento().getNome());

                            // Obt�m os atributos de talentos valorados
                            AtributoTalentoValorado objAtributoTalentoValorado = (AtributoTalentoValorado) lstAtributosTalentoValorados.get(j);

                            // Seta id do Talento para controle do grupo de talentos no relat�rio
                            objTalentoRelatorioDTO.setIdTalento(objAtributoTalentoValorado.getTalento().getIdentificador().toString());

                            // Seta id do Atributo talento para controle do grupo atributos no relat�rio
                            objTalentoRelatorioDTO.setIdAtributo(objAtributoTalentoValorado.getIdentificador().toString());

                            if (intIdCategoriaAtributoAnterior != objAtributoTalentoValorado.getCategoriaAtributoTalento().getIdentificador().intValue())
                            {
                                if ("Sim".equals(objAtributoTalentoValorado.getValoracao()))
                                {
                                    if (objAtributoTalentoValorado.getCategoriaAtributoTalento().getApelido() == null
                                            || "".equals(objAtributoTalentoValorado.getCategoriaAtributoTalento().getApelido()))
                                    {
                                        objTalentoRelatorioDTO.setDescricaoAtributo(objAtributoTalentoValorado.getCategoriaAtributoTalento().getAtributoTalento().getNome());
                                    }
                                    else
                                    {
                                        objTalentoRelatorioDTO.setDescricaoAtributo(objAtributoTalentoValorado.getCategoriaAtributoTalento().getApelido());
                                    }
                                }
                                else if (!"N�o".equals(objAtributoTalentoValorado.getValoracao()) && !"Sim".equals(objAtributoTalentoValorado.getValoracao()))
                                {
                                    if (objAtributoTalentoValorado.getCategoriaAtributoTalento().getApelido() == null
                                            || "".equals(objAtributoTalentoValorado.getCategoriaAtributoTalento().getApelido()))
                                    {
                                        objTalentoRelatorioDTO.setDescricaoAtributo(objAtributoTalentoValorado.getCategoriaAtributoTalento().getAtributoTalento().getNome());
                                    }
                                    else
                                    {
                                        objTalentoRelatorioDTO.setDescricaoAtributo(objAtributoTalentoValorado.getCategoriaAtributoTalento().getApelido());
                                    }
                                    if (objAtributoTalentoValorado.getValoracao() != null && !"".equals(objAtributoTalentoValorado.getValoracao()))
                                    {
                                        objTalentoRelatorioDTO.setValorAtributo(objAtributoTalentoValorado.getValoracao());
                                    }
                                    else
                                    {
                                        objTalentoRelatorioDTO.setValorAtributo("");
                                    }
                                }
                            }
                            else
                            {
                                if (objAtributoTalentoValorado.getValoracao() != null && !"".equals(objAtributoTalentoValorado.getValoracao()))
                                {
                                    objTalentoRelatorioDTO.setValorAtributo(objAtributoTalentoValorado.getValoracao());
                                }
                                else
                                {
                                    objTalentoRelatorioDTO.setValorAtributo("");
                                }
                            }
                            lstTalentoRelatorioDTO.add(objTalentoRelatorioDTO);
                            intIdCategoriaAtributoAnterior = objAtributoTalentoValorado.getCategoriaAtributoTalento().getIdentificador().intValue();
                        }
                    }
                    objCurriculoRelatorioDTO.setTalentoRelatorioDTO(lstTalentoRelatorioDTO);
                    lstRetorno.add(objCurriculoRelatorioDTO);
                }
            }
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
