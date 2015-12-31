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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.gov.camara.biblioteca.util.DataNova;
import br.gov.camara.negocio.DAO;
import br.gov.camara.negocio.bancotalentos.facade.ConsultaTalentoFacade;
import br.gov.camara.negocio.bancotalentos.pojo.ConsultaTalento;
import br.gov.camara.negocio.bancotalentos.pojo.ParametroConsultaTalento;
import br.gov.camara.negocio.bancotalentos.pojo.Pessoa;
import br.gov.camara.negocio.bancotalentos.util.BancoTalentosUtil;
import br.gov.camara.negocio.bancotalentos.util.GerenciadorAtributoVirtual;
import br.gov.camara.negocio.exception.DAOException;
import br.gov.camara.seguranca.UsuarioAutenticado;

/**
 * Classe DAO para montar a consulta de talentos
 */
public class ConsultaTalentoDAO extends DAO
{
    // Variáveis de instância 
    private static Log log = LogFactory.getLog(ConsultaTalentoDAO.class);

    /**
     * Construtor sem argumentos 
     */
    public ConsultaTalentoDAO()
    {
        super("Consulta de talentos");
    }

    /**
     * Não implementado
     * 
     */
    public List obterTodos() throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Dipara exceção
        throw new DAOException("Método não implementado");
    }

    /**
     * Método não implementado
     * 
     */
    public String excluirImpl(Object objAtributoTalento) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Dipara exceção
        throw new DAOException("Método não implementado");
    }

    /**
     * Efetua consulta de talentos
     * 
     * @param mapConsulta Contendo os parâmetros de consulta
     * @param objPessoa Pessoa que está realizando a consulta
     * @param lstPerfisSistema Perfis de sistema os quais a pessoa pertence
     * @param intMaximoPermitido Máximo de registros permitidos para o retorno da consulta
     * @param stbTotalRetornado Total de registros retornados
     * 
     * @return List Contendo o resultado da consulta, se o número máximo não for ultrapassado
     * 
     * @throws DAOException se ocorrer um erro relacionado com banco de dados 
     */

    public List consultar(Map mapConsulta, UsuarioAutenticado objPessoa, int intMaximoPermitido, StringBuffer stbTotalRetornado, boolean blnFiltroGrupo) throws DAOException, IOException
    {
        // Log
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Retorno
        List lstRetorno = new ArrayList();

        // Declarações
        Object[] objConsulta = null;
        List[] arrayPartesSubQuery = null;
        String strGrupos = null;
        List lstQuery = new ArrayList();
        List lstFromTalento = new ArrayList();
        List lstWhereTalento = new ArrayList();
        int intSequencialPessoa = 0;
        int intSequencialTalento = 0;
        int intSequencialAtributoTalentoValorado = 0;
        String strConector = "";

        // Verifica se há filtro por grupo
        if (blnFiltroGrupo)
        {
            FiltroConsultaDAO objFiltroConsultaDAO = new FiltroConsultaDAO();
            strGrupos = objFiltroConsultaDAO.obterGruposHabilitadosConsultaCurriculo(objPessoa.obterIdentificador().toString());
        }

        arrayPartesSubQuery = criarPartesSubQuery(strGrupos, intSequencialPessoa, intSequencialTalento);
        lstFromTalento = arrayPartesSubQuery[0];
        lstWhereTalento = arrayPartesSubQuery[1];

        // Cria query de consulta de talentos
        objConsulta = mapConsulta.keySet().toArray();

        // Itera critérios de consulta
        for (int i = 0; i < objConsulta.length; i++)
        {
            // Obtém beaConsulta
            ConsultaTalento criterioConsultaTalento = (ConsultaTalento) mapConsulta.get(objConsulta[i]);

            // Cria query de pessoa, se houver um UNION (OR)
            if (criterioConsultaTalento.getCriterioConsultaTalento().getIdentificador().equals(new Integer(2)) && i > 0)
            {
                lstQuery.add(new Object[] { lstFromTalento, lstWhereTalento });

                arrayPartesSubQuery = criarPartesSubQuery(strGrupos, intSequencialPessoa, intSequencialTalento);
                lstFromTalento = arrayPartesSubQuery[0];
                lstWhereTalento = arrayPartesSubQuery[1];
            }

            // Se a categoria for DADOS PESSOAIS
            if (criterioConsultaTalento.getCategoriaTalento().getIdentificador().intValue() == -1)
            {
                Iterator itrPametros = criterioConsultaTalento.getListaParametrosConsulta().iterator();

                // Itera consulta
                while (itrPametros.hasNext())
                {
                    // Obtém dados
                    ParametroConsultaTalento objParametroConsultaTalento = (ParametroConsultaTalento) itrPametros.next();

                    if (objParametroConsultaTalento.getCategoriaAtributoTalento().getAtributoTalento().getIdentificador().equals(ConsultaTalentoFacade.ID_ATRIBUTO_NOME))
                    {
                        // Monta opções para nome
                        if (lstWhereTalento.isEmpty())
                        {
                            strConector = " WHERE ";
                        }
                        else
                        {
                            strConector = " AND ";
                        }

                        lstWhereTalento.add(strConector
                                + " UPPER(pessoa"
                                + intSequencialPessoa
                                + ".nome) LIKE '%"
                                + objParametroConsultaTalento.getAtributoTalentoOpcao().getDescricao().toUpperCase().replaceAll("'", "''")
                                + "%'");
                    }
                    else if (objParametroConsultaTalento.getCategoriaAtributoTalento().getAtributoTalento().getIdentificador().equals(ConsultaTalentoFacade.ID_ATRIBUTO_IDADE))
                    {
                        String strDataInicial = null;
                        String strDataFinal = null;

                        // Monta opções para idade inicial
                        if (objParametroConsultaTalento.getAtributoTalentoOpcao().getDescricao() != null
                                && !"".equals(objParametroConsultaTalento.getAtributoTalentoOpcao().getDescricao()))
                        {
                            Calendar calDataInicial = new GregorianCalendar();
                            calDataInicial.add(Calendar.YEAR, -(Integer.parseInt(String.valueOf(objParametroConsultaTalento.getAtributoTalentoOpcao().getDescricao()))));
                            strDataInicial = "'" + formatarParametroData(calDataInicial.getTime()) + "'";
                        }

                        // Monta opções para idade final
                        if (objParametroConsultaTalento.getOpcaoComplementar() != null && !"".equals(objParametroConsultaTalento.getOpcaoComplementar()))
                        {
                            Calendar calDataFinal = new GregorianCalendar();
                            calDataFinal.add(Calendar.YEAR, -(Integer.parseInt(objParametroConsultaTalento.getOpcaoComplementar())));
                            calDataFinal.add(Calendar.DAY_OF_MONTH, 1);
                            calDataFinal.add(Calendar.YEAR, -1);
                            strDataFinal = "'" + formatarParametroData(calDataFinal.getTime()) + "'";
                        }

                        if (lstWhereTalento.isEmpty())
                        {
                            strConector = " WHERE ";
                        }
                        else
                        {
                            strConector = " AND ";
                        }
                        lstWhereTalento.add(strConector + " pessoa" + intSequencialPessoa + ".dataNascimento");

                        if (strDataInicial != null && strDataFinal != null)
                        {
                            lstWhereTalento.add(" BETWEEN " + strDataFinal + " AND " + strDataInicial);
                        }
                        else if (strDataFinal != null)
                        {
                            lstWhereTalento.add(" >= " + strDataFinal);
                        }
                        else if (strDataInicial != null)
                        {
                            lstWhereTalento.add(" <= " + strDataInicial);
                        }
                    }
                    else if (objParametroConsultaTalento.getCategoriaAtributoTalento().getAtributoTalento().getIdentificador().equals(ConsultaTalentoFacade.ID_ATRIBUTO_SEXO))
                    {
                        // Monta opções para sexo
                        if (lstWhereTalento.isEmpty())
                        {
                            strConector = " WHERE ";
                        }
                        else
                        {
                            strConector = " AND ";
                        }
                        lstWhereTalento.add(strConector
                                + " pessoa"
                                + intSequencialPessoa
                                + ".sexo = '"
                                + objParametroConsultaTalento.getAtributoTalentoOpcao().getDescricao().substring(0, 1).toUpperCase()
                                + "'");
                    }
                }
            }
            // Se POSSUI preenchimento
            else if (criterioConsultaTalento.getTipoConsultaTalento().getIdentificador().equals(new Integer(1)))
            {
                // Monta novo talento
                lstFromTalento.add(", Talento talento" + ++intSequencialTalento);
                if (lstWhereTalento.isEmpty())
                {
                    strConector = " WHERE ";
                }
                else
                {
                    strConector = " AND ";
                }
                lstWhereTalento.add(strConector
                        + " talento"
                        + intSequencialTalento
                        + ".categoriaTalento.identificador = "
                        + criterioConsultaTalento.getCategoriaTalento().getIdentificador()
                        + " AND talento"
                        + intSequencialTalento
                        + ".pessoa.identificador = pessoa"
                        + intSequencialPessoa
                        + ".identificador");

                if (criterioConsultaTalento.getListaParametrosConsulta() != null)
                {
                    Iterator itrPametros = criterioConsultaTalento.getListaParametrosConsulta().iterator();

                    // Itera consulta
                    while (itrPametros.hasNext())
                    {
                        // Obtém dados
                        ParametroConsultaTalento objParametroConsultaTalento = (ParametroConsultaTalento) itrPametros.next();

                        lstFromTalento.add(", AtributoTalentoValorado atributoTalentoValorado" + ++intSequencialAtributoTalentoValorado);
                        lstWhereTalento.add(" AND talento"
                                + intSequencialTalento
                                + ".identificador = atributoTalentoValorado"
                                + intSequencialAtributoTalentoValorado
                                + ".talento.identificador"
                                + " AND atributoTalentoValorado"
                                + intSequencialAtributoTalentoValorado
                                + ".categoriaAtributoTalento.identificador = "
                                + objParametroConsultaTalento.getCategoriaAtributoTalento().getIdentificador());

                        if (objParametroConsultaTalento.getAtributoTalentoOpcao() != null)
                        {
                            if (objParametroConsultaTalento.getOpcaoComplementar() == null)
                            {

                                //Trata atributo virtual
                                if (GerenciadorAtributoVirtual.isAtributoVirtual(objParametroConsultaTalento.getCategoriaAtributoTalento().getAtributoTalento().getNome()))
                                {
                                    lstWhereTalento.add(" AND UPPER(atributoTalentoValorado"
                                            + intSequencialAtributoTalentoValorado
                                            + ".valoracao) like ('%"
                                            + objParametroConsultaTalento.getAtributoTalentoOpcao().getDescricao().toUpperCase()
                                            + "%')");
                                }
                                else if (objParametroConsultaTalento.getAtributoTalentoOpcao().getIdentificador() == null)
                                {
                                    lstWhereTalento.add(" AND UPPER(atributoTalentoValorado"
                                            + intSequencialAtributoTalentoValorado
                                            + ".valoracao) LIKE '%"
                                            + objParametroConsultaTalento.getAtributoTalentoOpcao().getDescricao().toUpperCase().replaceAll("'", "''")
                                            + "%'");
                                }
                                else
                                {
                                    lstWhereTalento.add(" AND atributoTalentoValorado"
                                            + intSequencialAtributoTalentoValorado
                                            + ".atributoTalentoOpcao.identificador = "
                                            + objParametroConsultaTalento.getAtributoTalentoOpcao().getIdentificador());
                                }
                            }
                            else
                            {
                                boolean isData = false;
                                if (DataNova.validarData(objParametroConsultaTalento.getAtributoTalentoOpcao().getDescricao())
                                        || DataNova.validarData(objParametroConsultaTalento.getOpcaoComplementar()))
                                {
                                    isData = true;
                                }

                                if (!"".equals(objParametroConsultaTalento.getAtributoTalentoOpcao().getDescricao())
                                        && !"".equals(objParametroConsultaTalento.getOpcaoComplementar()))
                                {
                                    if (isData)
                                    {
                                        lstWhereTalento.add(" AND ("
                                                + BancoTalentosUtil.utilizarFuncaoBDConversaoStringEmData(this, "atributoTalentoValorado"
                                                        + intSequencialAtributoTalentoValorado
                                                        + ".valoracao")
                                                + " BETWEEN '"
                                                + formatarParametroData(DataNova.converter(objParametroConsultaTalento.getAtributoTalentoOpcao().getDescricao()))
                                                + "' AND '"
                                                + formatarParametroData(DataNova.converter(objParametroConsultaTalento.getOpcaoComplementar()))
                                                + "')");
                                    }
                                    else
                                    {
                                        lstWhereTalento.add(" AND atributoTalentoValorado"
                                                + intSequencialAtributoTalentoValorado
                                                + ".valoracao BETWEEN "
                                                + objParametroConsultaTalento.getAtributoTalentoOpcao().getDescricao()
                                                + " AND "
                                                + objParametroConsultaTalento.getOpcaoComplementar());
                                    }
                                }
                                else if ("".equals(objParametroConsultaTalento.getAtributoTalentoOpcao().getDescricao())
                                        && !"".equals(objParametroConsultaTalento.getOpcaoComplementar()))
                                {
                                    if (isData)
                                    {
                                        lstWhereTalento.add(" AND ("
                                                + BancoTalentosUtil.utilizarFuncaoBDConversaoStringEmData(this, "atributoTalentoValorado"
                                                        + intSequencialAtributoTalentoValorado
                                                        + ".valoracao")
                                                + " <= '"
                                                + formatarParametroData(DataNova.converter(objParametroConsultaTalento.getOpcaoComplementar()))
                                                + "')");
                                    }
                                    else
                                    {
                                        lstWhereTalento.add(" AND atributoTalentoValorado"
                                                + intSequencialAtributoTalentoValorado
                                                + ".valoracao <= "
                                                + objParametroConsultaTalento.getOpcaoComplementar());
                                    }
                                }
                                else if (!"".equals(objParametroConsultaTalento.getAtributoTalentoOpcao().getDescricao())
                                        && "".equals(objParametroConsultaTalento.getOpcaoComplementar()))
                                {
                                    if (isData)
                                    {
                                        lstWhereTalento.add(" AND ("
                                                + BancoTalentosUtil.utilizarFuncaoBDConversaoStringEmData(this, "atributoTalentoValorado"
                                                        + intSequencialAtributoTalentoValorado
                                                        + ".valoracao")
                                                + " >= '"
                                                + formatarParametroData(DataNova.converter(objParametroConsultaTalento.getAtributoTalentoOpcao().getDescricao()))
                                                + "')");
                                    }
                                    else
                                    {
                                        lstWhereTalento.add(" AND atributoTalentoValorado"
                                                + intSequencialAtributoTalentoValorado
                                                + ".valoracao >= "
                                                + objParametroConsultaTalento.getAtributoTalentoOpcao().getDescricao());
                                    }
                                }

                            }
                        }

                    }
                }

            }
            // Se NÃO POSSUI preenchimento
            else if (criterioConsultaTalento.getTipoConsultaTalento().getIdentificador().equals(new Integer(2)))
            {
                List lstWhereNotExists = new ArrayList();
                if (lstWhereTalento.isEmpty())
                {
                    strConector = " WHERE ";
                }
                else
                {
                    strConector = " AND ";
                }
                lstWhereTalento.add(strConector
                        + " NOT EXISTS (SELECT pessoa"
                        + ++intSequencialTalento
                        + ".identificador"
                        + " FROM Pessoa pessoa"
                        + intSequencialTalento);
                lstWhereTalento.add(", Talento talento" + intSequencialTalento);
                lstWhereNotExists.add(" WHERE pessoa" + intSequencialPessoa + ".identificador = pessoa" + intSequencialTalento + ".identificador");
                lstWhereNotExists.add(" AND talento"
                        + intSequencialTalento
                        + ".pessoa.identificador = pessoa"
                        + intSequencialPessoa
                        + ".identificador"
                        + " AND talento"
                        + intSequencialTalento
                        + ".categoriaTalento.identificador = "
                        + criterioConsultaTalento.getCategoriaTalento().getIdentificador());

                if (criterioConsultaTalento.getListaParametrosConsulta() != null)
                {
                    Iterator itrPametros = criterioConsultaTalento.getListaParametrosConsulta().iterator();

                    // Itera consulta
                    while (itrPametros.hasNext())
                    {
                        // Obtém dados
                        ParametroConsultaTalento objParametroConsultaTalento = (ParametroConsultaTalento) itrPametros.next();

                        lstWhereTalento.add(", AtributoTalentoValorado atributoTalentoValorado" + ++intSequencialAtributoTalentoValorado);
                        lstWhereNotExists.add(" AND talento"
                                + intSequencialTalento
                                + ".identificador = atributoTalentoValorado"
                                + intSequencialAtributoTalentoValorado
                                + ".talento.identificador"
                                + " AND atributoTalentoValorado"
                                + intSequencialAtributoTalentoValorado
                                + ".categoriaAtributoTalento.identificador = "
                                + objParametroConsultaTalento.getCategoriaAtributoTalento().getIdentificador());
                        if (objParametroConsultaTalento.getAtributoTalentoOpcao() != null)
                        {

                            if (objParametroConsultaTalento.getOpcaoComplementar() == null)
                            {
                                if (objParametroConsultaTalento.getAtributoTalentoOpcao().getIdentificador() == null)
                                {
                                    lstWhereNotExists.add(" AND UPPER(atributoTalentoValorado"
                                            + intSequencialAtributoTalentoValorado
                                            + ".valoracao) LIKE '%"
                                            + objParametroConsultaTalento.getAtributoTalentoOpcao().getDescricao().toUpperCase().replaceAll("'", "''")
                                            + "%'");
                                }
                                else
                                {
                                    lstWhereNotExists.add(" AND atributoTalentoValorado"
                                            + intSequencialAtributoTalentoValorado
                                            + ".atributoTalentoOpcao.identificador = "
                                            + objParametroConsultaTalento.getAtributoTalentoOpcao().getIdentificador());
                                }
                            }
                            else
                            {
                                boolean isData = false;
                                if (DataNova.validarData(objParametroConsultaTalento.getAtributoTalentoOpcao().getDescricao())
                                        || DataNova.validarData(objParametroConsultaTalento.getOpcaoComplementar()))
                                {
                                    isData = true;
                                }

                                if (!"".equals(objParametroConsultaTalento.getAtributoTalentoOpcao().getDescricao())
                                        && !"".equals(objParametroConsultaTalento.getOpcaoComplementar()))
                                {
                                    if (isData)
                                    {
                                        lstWhereNotExists.add(" AND ("
                                                + BancoTalentosUtil.utilizarFuncaoBDConversaoStringEmData(this, "atributoTalentoValorado"
                                                        + intSequencialAtributoTalentoValorado
                                                        + ".valoracao")
                                                + " BETWEEN '"
                                                + formatarParametroData(DataNova.converter(objParametroConsultaTalento.getAtributoTalentoOpcao().getDescricao()))
                                                + "' AND '"
                                                + formatarParametroData(DataNova.converter(objParametroConsultaTalento.getOpcaoComplementar()))
                                                + "')");
                                    }
                                    else
                                    {
                                        lstWhereNotExists.add(" AND atributoTalentoValorado"
                                                + intSequencialAtributoTalentoValorado
                                                + ".valoracao BETWEEN "
                                                + objParametroConsultaTalento.getAtributoTalentoOpcao().getDescricao()
                                                + " AND "
                                                + objParametroConsultaTalento.getOpcaoComplementar());
                                    }
                                }
                                else if ("".equals(objParametroConsultaTalento.getAtributoTalentoOpcao().getDescricao())
                                        && !"".equals(objParametroConsultaTalento.getOpcaoComplementar()))
                                {
                                    if (isData)
                                    {
                                        lstWhereNotExists.add(" AND ("
                                                + BancoTalentosUtil.utilizarFuncaoBDConversaoStringEmData(this, "atributoTalentoValorado"
                                                        + intSequencialAtributoTalentoValorado
                                                        + ".valoracao")
                                                + " <= '"
                                                + formatarParametroData(DataNova.converter(objParametroConsultaTalento.getOpcaoComplementar()))
                                                + "')");
                                    }
                                    else
                                    {
                                        lstWhereNotExists.add(" AND atributoTalentoValorado"
                                                + intSequencialAtributoTalentoValorado
                                                + ".valoracao <= "
                                                + objParametroConsultaTalento.getOpcaoComplementar());
                                    }
                                }
                                else if (!"".equals(objParametroConsultaTalento.getAtributoTalentoOpcao().getDescricao())
                                        && "".equals(objParametroConsultaTalento.getOpcaoComplementar()))
                                {
                                    if (isData)
                                    {
                                        lstWhereNotExists.add(" AND ("
                                                + BancoTalentosUtil.utilizarFuncaoBDConversaoStringEmData(this, "atributoTalentoValorado"
                                                        + intSequencialAtributoTalentoValorado
                                                        + ".valoracao")
                                                + " >= '"
                                                + formatarParametroData(DataNova.converter(objParametroConsultaTalento.getOpcaoComplementar()))
                                                + "')");
                                    }
                                    else
                                    {
                                        lstWhereNotExists.add(" AND atributoTalentoValorado"
                                                + intSequencialAtributoTalentoValorado
                                                + ".valoracao >= "
                                                + objParametroConsultaTalento.getAtributoTalentoOpcao().getDescricao());
                                    }
                                }

                            }
                        }
                    }

                }
                lstWhereTalento.addAll(lstWhereNotExists);
                lstWhereTalento.add(")");
            }
        }

        // Monta a consulta de talentos
        lstQuery.add(new Object[] { lstFromTalento, lstWhereTalento });
        Iterator itrQueries = lstQuery.iterator();
        while (itrQueries.hasNext())
        {
            // Monta query
            String strQuery = "";
            Object[] objQueries = (Object[]) itrQueries.next();
            for (int i = 0; i < objQueries.length; i++)
            {
                Iterator itrFragmentos = ((List) objQueries[i]).iterator();
                while (itrFragmentos.hasNext())
                {
                    strQuery += (String) itrFragmentos.next();
                }
            }

            // Verifica o total de registros da query
            int intTotalRetornado = obterTotalRegistros("SELECT COUNT(DISTINCT talento.pessoa.identificador)" + strQuery);
            stbTotalRetornado.delete(0, stbTotalRetornado.length());
            stbTotalRetornado.append(intTotalRetornado);
            if ((intMaximoPermitido == -1) || ((intTotalRetornado + lstRetorno.size()) <= intMaximoPermitido))
            {
                // Efetua consulta
                List lstRetornoTemp = obter("SELECT DISTINCT talento.pessoa" + strQuery + " ORDER BY pessoa" + intSequencialPessoa + ".nome");
                PessoaDAO objPessoaDAO = new PessoaDAO();
                for (int i = 0; i < lstRetornoTemp.size(); i++)
                {
                    if (!lstRetorno.contains(lstRetornoTemp.get(i)))
                    {
                        Pessoa objPessoaResultado = (Pessoa) lstRetornoTemp.get(i);
                        objPessoaDAO.inicializarGrupo(objPessoaResultado);
                        lstRetorno.add(objPessoaResultado);
                    }
                }
            }
            else
            {
                lstRetorno = null;
                break;
            }
        }

        // Retorno
        return lstRetorno;
    }

    private List[] criarPartesSubQuery(String strFiltroGrupos, int intSequencialPessoa, int intSequencialTalento)
    {
        List lstFrom = new ArrayList();
        List lstWhere = new ArrayList();

        // Cria query de pessoa com talentos cadastrados
        lstFrom.add(" FROM Talento talento JOIN talento.pessoa AS pessoa" + intSequencialPessoa);

        if (strFiltroGrupos != null)
        {
            lstWhere.add(" WHERE talento.pessoa.grupo.codigo IN (" + strFiltroGrupos + ")");
        }

        return new List[] { lstFrom, lstWhere };
    }

}
