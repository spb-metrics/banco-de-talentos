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

package br.gov.camara.negocio.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import br.gov.camara.biblioteca.util.ClasseDinamica;
import br.gov.camara.biblioteca.util.Copia;
import br.gov.camara.biblioteca.util.DataNova;
import br.gov.camara.biblioteca.util.ParametroDinamico;
import br.gov.camara.exception.CDException;

/**
 * Classe para permitir consultas genéricas para serviços
 */
public class Consulta
{
    // Constantes estáticas
    public static final int TEXTO = 0;

    public static final int NUMERO = 1;

    public static final int DATA = 2;

    public static final int DATA_INICIAL = 3;

    public static final int DATA_FINAL = 4;

    public static final int DATA_MAIOR = 20;

    public static final int DATA_MENOR = 21;

    public static final int DATA_MAIOR_OU_IGUAL = 5;

    public static final int DATA_MENOR_OU_IGUAL = 6;

    public static final int ESCONDIDO = 7; // valor nao pode ser alterado

    public static final int INTERVALO = 8;

    public static final int DIFERENTE_DE_NUMERO = 9;

    public static final int DIFERENTE_DE_TEXTO = 10;

    public static final int DIFERENTE_DE_DATA = 11;

    public static final int NUMERO_INICIAL = 12;

    public static final int NUMERO_FINAL = 13;

    public static final int NUMERO_MAIOR = 14;

    public static final int NUMERO_MENOR = 15;

    public static final int NUMERO_MAIOR_OU_IGUAL = 16;

    public static final int NUMERO_MENOR_OU_IGUAL = 17;

    public static final int TEXTO_INICIAL = 18;

    public static final int TEXTO_FINAL = 19;

    public static final int TEXTO_ESCONDIDO = 23; // valor nao pode ser alterado

    public static final int INTERVALO_TEXTO = 22;

    public static final int INTERVALO_DATA = 24;

    public static final int IS_NULL = 25;

    public static final int INTERVALO_NOT_IN = 26;

    public static final int IS_NOT_NULL = 27;

    public static final int REGISTROS_POR_PAGINA_PADRAO = 20; // "".equals(AplicacaoPlugIn.obterConfiguracao("REGISTROS_POR_PAGINA_PADRAO"))
    // ? 20 :
    // Integer.parseInt(AplicacaoPlugIn.obterConfiguracao("REGISTROS_POR_PAGINA_PADRAO"));

    // Variáveis de instância
    private String strClasse = null;
    private ConsultaComum objClasse = null;

    private String strNomeConexao = null;

    private List lstCamposFiltro = new ArrayList();

    private List lstCamposVisualizacao = new ArrayList();

    private List lstCamposRetorno = new ArrayList();

    private List lstCriteriosFiltro = new ArrayList();

    private List lstCamposOrdenacao = new ArrayList();

    private boolean blnCarregamento = false;

    // adiciona uma condicao extra para a consulta
    private String strCondicaoExtra;

    private int intFormatoData = DataNova.FORMAT_MMDDYYYY;

    public Consulta()
    {

    }

    public Consulta(int intFormatoData)
    {
        this.intFormatoData = intFormatoData;
    }

    public void setFormatoData(int intFormatoData)
    {
        this.intFormatoData = intFormatoData;
    }

    /**
     * Atribui objeto
     * 
     * @param strClasse String contendo o caminho completo da classe a ser instanciada
     */
    public void setClasse(String strClasse)
    {
        this.objClasse = null;
        this.strClasse = strClasse;
    }

    /**
     * Atribui objeto
     * 
     * @param strClasse String contendo o caminho completo da classe a ser instanciada
     */
    public void setNomeConexao(String strNomeConexao)
    {
        this.objClasse = null;
        this.strNomeConexao = strNomeConexao;
    }

    /**
     * Atribui indicação de carregamento (se houver carregamento antes do filtro, true, senão, false)
     * 
     * @param blnCarregamento Indica se deve ou não ser feito o carregamento
     */
    public void setCarregamento(boolean blnCarregamento)
    {
        this.blnCarregamento = blnCarregamento;
    }

    /**
     * Obtém indicação de carregamento (se houver carregamento antes do filtro, true, senão, false)
     * 
     * @return boolean Contendo o dado
     */
    public boolean getCarregamento()
    {
        return this.blnCarregamento;
    }

    /**
     * Obtém lista de campos de filtro
     * 
     * @return List Contendo o dado
     */
    public List getCamposFiltro()
    {
        return this.lstCamposFiltro;
    }

    /**
     * Obtém lista de campos de visualizacão
     * 
     * @return List Contendo o dado
     */
    public List getCamposVisualizacao()
    {
        return this.lstCamposVisualizacao;
    }

    /**
     * Obtém lista de campos de retorno
     * 
     * @return List Contendo o dado
     */
    public List getCamposRetorno()
    {
        return this.lstCamposRetorno;
    }

    /**
     * Obtém lista de critérios de filtro
     * 
     * @return List Contendo o dado
     */
    public List getCriteriosFiltro()
    {
        return this.lstCriteriosFiltro;
    }

    /**
     * Adiciona campo para filtro
     * 
     * @param strTitulo Título da coluna de filtro
     * @param strColuna Nome da coluna de filtro
     * @param intTipo Tipo da coluna de filtro (utilizar constantes estáticas)
     */
    public void adicionarCampoFiltro(String strTitulo, String strColuna, int intTipo)
    {
        // Verifica se o tipo está no range aceitável (0 a 27)
        if (intTipo < TEXTO || intTipo > IS_NOT_NULL)
        {
            throw new RuntimeException("O tipo especificado não é válido");
        }

        // Monta objeto filtro
        Filtro objFiltro = new Filtro();
        objFiltro.setTitulo(strTitulo);
        objFiltro.setColuna(strColuna);
        objFiltro.setTipo(intTipo);
        this.lstCamposFiltro.add(objFiltro);
    }

    /**
     * Adiciona campo para filtro
     * 
     * @param strTitulo Título da coluna de filtro
     * @param strColuna Nome da coluna de filtro
     * @param intTipo Tipo da coluna de filtro (utilizar constantes estáticas)
     * @param strValorPadrao Valor padrao para o campo
     */
    public void adicionarCampoFiltro(String strTitulo, String strColuna, int intTipo, String strValorPadrao)
    {
        // Verifica se o tipo está no range aceitável (0 a 26)
        if (intTipo < TEXTO || intTipo > INTERVALO_NOT_IN)
        {
            throw new RuntimeException("O tipo especificado não é válido");
        }

        // Monta objeto filtro
        Filtro objFiltro = new Filtro();
        objFiltro.setTitulo(strTitulo);
        objFiltro.setColuna(strColuna);
        objFiltro.setTipo(intTipo);
        objFiltro.setValorPadrao(strValorPadrao);
        this.lstCamposFiltro.add(objFiltro);
    }

    /**
     * Adiciona campo para filtro
     * 
     * @param strTitulo Título da coluna de filtro
     * @param strColuna Nome da coluna de filtro
     * @param intTipo Tipo da coluna de filtro (utilizar constantes estáticas)
     * @param blnIdentificador Flag que indica se o campo é ou não identificador
     */
    public void adicionarCampoFiltro(String strTitulo, String strColuna, int intTipo, boolean blnIdentificador)
    {
        // Verifica se o tipo está no range aceitável (0 a 26)
        if (intTipo < TEXTO || intTipo > INTERVALO_NOT_IN)
        {
            throw new RuntimeException("O tipo especificado não é válido");
        }

        // Monta objeto filtro
        Filtro objFiltro = new Filtro();
        objFiltro.setTitulo(strTitulo);
        objFiltro.setColuna(strColuna);
        objFiltro.setTipo(intTipo);
        objFiltro.setIdentificador(blnIdentificador);
        this.lstCamposFiltro.add(objFiltro);
    }

    /**
     * Adiciona campo para filtro
     * 
     * @param strTitulo Título da coluna de filtro
     * @param strColuna Nome da coluna de filtro
     * @param intTipo Tipo da coluna de filtro (utilizar constantes estáticas)
     * @param blnIdentificador Flag que indica se o campo é ou não identificador
     * @param strConectorWhere Conector entre os filtros na cláusula WHERE
     */
    public void adicionarCampoFiltro(String strTitulo, String strColuna, int intTipo, boolean blnIdentificador, String strConectorWhere)
    {
        // Verifica se o tipo está no range aceitável (0 a 26)
        if (intTipo < TEXTO || intTipo > INTERVALO_NOT_IN)
        {
            throw new RuntimeException("O tipo especificado não é válido");
        }

        if (!strConectorWhere.trim().equalsIgnoreCase("AND") && !strConectorWhere.trim().equalsIgnoreCase("OR"))
        {
            throw new RuntimeException("O conector utilizado(" + strConectorWhere.trim() + ") na cláusula WHERE não é válido");
        }

        // Monta objeto filtro
        Filtro objFiltro = new Filtro();
        objFiltro.setTitulo(strTitulo);
        objFiltro.setColuna(strColuna);
        objFiltro.setTipo(intTipo);
        objFiltro.setIdentificador(blnIdentificador);
        objFiltro.setConectorWhere(strConectorWhere);
        this.lstCamposFiltro.add(objFiltro);
    }

    /**
     * Adiciona campo para o filtro
     * 
     * @param strTitulo Título da coluna de filtro
     * @param strColuna Nome da coluna de filtro
     * @param intTipo Tipo da coluna de filtro (utilizar constantes estaticas)
     * @param strConectorWhere Conector entre os filtros na cláusula WHERE
     * @param strValorPadrao Valor padrao para o campo
     */
    public void adicionarCampoFiltro(String strTitulo, String strColuna, int intTipo, String strConectorWhere, String strValorPadrao)
    {
        // Verifica se o tipo está no range aceitável (0 a 26)
        if (intTipo < TEXTO || intTipo > INTERVALO_NOT_IN)
        {
            throw new RuntimeException("O tipo especificado não é válido");
        }

        if (!strConectorWhere.trim().equalsIgnoreCase("AND") && !strConectorWhere.trim().equalsIgnoreCase("OR"))
        {
            throw new RuntimeException("O conector utilizado(" + strConectorWhere.trim() + ") na cláusula WHERE não é válido");
        }

        // Monta objeto filtro
        Filtro objFiltro = new Filtro();
        objFiltro.setTitulo(strTitulo);
        objFiltro.setColuna(strColuna);
        objFiltro.setTipo(intTipo);
        objFiltro.setConectorWhere(strConectorWhere);
        objFiltro.setValorPadrao(strValorPadrao);
        this.lstCamposFiltro.add(objFiltro);
    }

    /**
     * Adiciona campo para visualização
     * 
     * @param strTitulo Título da coluna de visualização
     * @param strColuna Nome da coluna de visualização
     */
    public void adicionarCampoVisualizacao(String strTitulo, String strColuna)
    {
        Campo objCampo = new Campo();
        objCampo.setTitulo(strTitulo);
        objCampo.setColuna(strColuna);
        this.lstCamposVisualizacao.add(objCampo);
    }

    /**
     * Adiciona campo de filtro para visualização
     * 
     * @param strTitulo Título da coluna de visualizacao
     * @param strColuna Nome da coluna de visualizacao
     * @param intTipo Tipo de dado da coluna
     */
    public void adicionarCampoVisualizacao(String strTitulo, String strColuna, int intTipo)
    {
        Filtro objFiltro = new Filtro();
        objFiltro.setTipo(intTipo);
        objFiltro.setTitulo(strTitulo);
        objFiltro.setColuna(strColuna);
        this.lstCamposVisualizacao.add(objFiltro);
    }

    /**
     * Adiciona campo para retorno
     * 
     * @param strColuna Nome da coluna de retorno
     */
    public void adicionarCampoRetorno(String strColuna)
    {
        this.lstCamposRetorno.add(strColuna);
    }

    /**
     * Adiciona critério para filtro (deve seguir a ordem dos campos de filtro)
     * 
     * @param strCriterio Critério para montagem de filtro
     */
    public void adicionarCriterioFiltro(String strCriterio)
    {
        this.lstCriteriosFiltro.add(strCriterio);
    }

    /**
     * Limpa todos os critérios de filtro
     */
    public void limparCriteriosFiltro()
    {
        this.lstCriteriosFiltro.clear();
    }

    /**
     * Limpa os campos do filtro
     */
    public void limparCamposFiltro()
    {
        this.lstCamposFiltro.clear();
    }

    /**
     * Limpa os campos de ordenação
     */
    public void limparCamposOrdenacao()
    {
        this.lstCamposOrdenacao.clear();
    }

    /**
     * Efetua a consulta para obter o total de registros
     * 
     * @param request requisição do usuário.
     * @return int total de registros.
     */
    public int obterTotalRegistros() throws CDException
    {
        // Declarações
        int intTotalRegistros = 0;

        ConsultaComum objConsultaComum = this.obterInstancia();
        intTotalRegistros = objConsultaComum.obterTotalRegistros(this);

        return intTotalRegistros;
    }

    /**
     * Efetua a consulta de acordo com o filtro especificado
     * 
     * @param request requisicao do usuario
     * @param intNumeroPagina página para efetuar a consulta
     * @param intMaximoPagina registros por página
     */
    public List consultar(int intNumeroPagina, int intMaximoPagina) throws CDException
    {
        // Declarações
        List lstRetorno = null;

        ConsultaComum objConsultaComum = this.obterInstancia();
        lstRetorno = objConsultaComum.consultar(this, intNumeroPagina, intMaximoPagina);

        return lstRetorno;
    }

    /**
     * Monta o filtro na sintaxe a ser utilizada nos objetos de negócio
     * 
     * @return String montada com os critérios selecionados
     */
    public String montarFiltro() throws CDException
    {
        // Verifica se existe o mesmo número de filtros e critérios
        if (this.lstCriteriosFiltro.size() > 0 && this.lstCamposFiltro.size() != this.lstCriteriosFiltro.size())
        {
            throw new CDException("O número de critérios de filtro deve ser o mesmo número de campos de filtro");
        }

        // String para montar o retorno
        StringBuffer strFiltro = new StringBuffer();

        // Itera filtros
        Iterator itrCriteriosFiltro = this.lstCriteriosFiltro.iterator();
        String strItem = "";
        String strProximoItem = "";
        StringBuffer strCriterio = null;

        int intIndice = 0;
        while (itrCriteriosFiltro.hasNext())
        {
            boolean blnUpper = false;
            strCriterio = new StringBuffer();
            strItem = (String) itrCriteriosFiltro.next();
            if (!"".equals(strItem))
            {
                Filtro objFiltro = (Filtro) this.lstCamposFiltro.get(intIndice);
                if (strItem != null || objFiltro.getTipo() == Consulta.IS_NULL || objFiltro.getTipo() == Consulta.IS_NOT_NULL)
                {
                    Filtro objProximoFiltro = null;
                    switch (objFiltro.getTipo())
                    {

                        case Consulta.NUMERO:
                        case Consulta.NUMERO_FINAL:
                            strCriterio.append(" = ").append(strItem);
                            break;

                        case Consulta.NUMERO_MAIOR:
                            strCriterio.append(" > ").append(strItem);
                            break;

                        case Consulta.NUMERO_MENOR:
                            strCriterio.append(" < ").append(strItem);
                            break;

                        case Consulta.NUMERO_MAIOR_OU_IGUAL:
                            strCriterio.append(" >= ").append(strItem);
                            break;

                        case Consulta.NUMERO_MENOR_OU_IGUAL:
                            strCriterio.append(" <= ").append(strItem);
                            break;

                        case Consulta.NUMERO_INICIAL:
                            objProximoFiltro = (Filtro) this.lstCamposFiltro.get(++intIndice);
                            if (objProximoFiltro.getTipo() != Consulta.NUMERO_FINAL)
                            {
                                throw new CDException("Ao especificar um campo do tipo NUMERO_INICIAL, " + "o campo posterior deve ser NUMERO_FINAL");
                            }
                            strProximoItem = (String) itrCriteriosFiltro.next();
                            if (!objFiltro.getColuna().equals(objProximoFiltro.getColuna()))
                            {
                                throw new CDException("A coluna especificada no campo do tipo NUMERO_INICIAL, "
                                        + "deve ser igual à coluna posterior especificado coma NUMERO_FINAL");
                            }
                            if (!"".equals(strProximoItem))
                            {
                                strCriterio.append(" BETWEEN ").append(strItem).append(" AND ").append(strProximoItem);
                            }
                            else
                            {
                                strCriterio.append(" = ").append(strItem);
                            }
                            break;

                        case Consulta.DIFERENTE_DE_NUMERO:
                            strCriterio.append(" <> ").append(strItem.toUpperCase());
                            break;

                        case Consulta.TEXTO:
                        case Consulta.TEXTO_FINAL:
                            blnUpper = true;
                            if (objFiltro.isIdentificador())
                            {
                                strCriterio.append(" = '").append(strItem.toUpperCase()).append("'");
                            }
                            else
                            {
                                strCriterio.append(" LIKE '%").append(strItem.toUpperCase()).append("%'");
                            }
                            break;

                        case Consulta.TEXTO_INICIAL:
                            blnUpper = true;
                            objProximoFiltro = (Filtro) this.lstCamposFiltro.get(++intIndice);
                            if (objProximoFiltro.getTipo() != Consulta.TEXTO_FINAL)
                            {
                                throw new CDException("Ao especificar um campo do tipo TEXTO_INICIAL, " + "o campo posterior deve ser TEXTO_FINAL");
                            }
                            strProximoItem = (String) itrCriteriosFiltro.next();
                            if (!objFiltro.getColuna().equals(objProximoFiltro.getColuna()))
                            {
                                throw new CDException("A coluna especificada no campo do tipo TEXTO_INICIAL, "
                                        + "deve ser igual à coluna posterior especificado coma TEXTO_FINAL");
                            }
                            if (!"".equals(strProximoItem))
                            {
                                strCriterio.append(" BETWEEN '").append(strItem.toUpperCase()).append("' AND '").append(strProximoItem.toUpperCase()).append("'");
                            }
                            else
                            {
                                strCriterio.append(" LIKE '%").append(strItem.toUpperCase()).append("%'");
                            }
                            break;

                        case Consulta.DIFERENTE_DE_TEXTO:
                            blnUpper = true;
                            strCriterio.append(" <> '").append(strItem.toUpperCase()).append("'");
                            break;

                        case Consulta.DATA:
                        case Consulta.DATA_FINAL:
                            strCriterio.append(" = '").append(DataNova.format(strItem, intFormatoData)).append("'");
                            break;

                        case Consulta.DATA_INICIAL:
                            objProximoFiltro = (Filtro) this.lstCamposFiltro.get(++intIndice);
                            if (objProximoFiltro.getTipo() != Consulta.DATA_FINAL)
                            {
                                throw new CDException("Ao especificar um campo do tipo DATA_INICIAL, " + "o campo posterior deve ser DATA_FINAL");
                            }
                            strProximoItem = (String) itrCriteriosFiltro.next();
                            if (!objFiltro.getColuna().equals(objProximoFiltro.getColuna()))
                            {
                                throw new CDException("A coluna especificada no campo do tipo DATA_INICIAL, "
                                        + "deve ser igual à coluna posterior especificado coma DATA_FINAL");
                            }
                            if (!"".equals(strProximoItem))
                            {
                                strCriterio.append(" BETWEEN '").append(DataNova.format(strItem, intFormatoData)).append("' AND '").append(DataNova.format(strProximoItem, intFormatoData)).append("'");
                            }
                            else
                            {
                                strCriterio.append(" = '").append(DataNova.format(strItem, intFormatoData)).append("'");
                            }
                            break;

                        case Consulta.DATA_MAIOR:
                            strCriterio.append(" > '").append(DataNova.format(strItem, intFormatoData)).append("'");
                            break;

                        case Consulta.DATA_MENOR:
                            strCriterio.append(" < '").append(DataNova.format(strItem, intFormatoData)).append("'");
                            break;

                        case Consulta.DATA_MAIOR_OU_IGUAL:
                            strCriterio.append(" >= '").append(DataNova.format(strItem, intFormatoData)).append("'");
                            break;

                        case Consulta.DATA_MENOR_OU_IGUAL:
                            strCriterio.append(" <= '").append(DataNova.format(strItem, intFormatoData)).append("'");
                            break;

                        case Consulta.DIFERENTE_DE_DATA:
                            blnUpper = true;
                            strCriterio.append(" <> '").append(DataNova.format(strItem, intFormatoData)).append("'");
                            break;

                        case Consulta.ESCONDIDO:
                            strCriterio.append(" = ").append(strItem);
                            break;

                        case Consulta.TEXTO_ESCONDIDO:
                            strCriterio.append(" = '").append(strItem).append("'");
                            break;

                        case Consulta.INTERVALO:
                            strCriterio.append(" IN (").append(strItem.toUpperCase()).append(")");
                            break;

                        case Consulta.INTERVALO_TEXTO:
                            blnUpper = true;
                            strCriterio.append(" IN ('").append(strItem.toUpperCase().replaceAll(",", "','")).append("')");
                            break;

                        case Consulta.INTERVALO_DATA:
                            String[] arrDatas = strItem.trim().split(",");
                            strCriterio.append(" IN (");

                            for (int j = 0; j < arrDatas.length; j++)
                            {
                                strCriterio.append("'").append(DataNova.format(arrDatas[j], intFormatoData)).append("',");
                            }

                            strCriterio.deleteCharAt(strCriterio.length() - 1).append(")");
                            break;

                        case Consulta.IS_NULL:
                            strCriterio.append(" IS NULL");
                            break;

                        case Consulta.IS_NOT_NULL:
                            strCriterio.append(" IS NOT NULL");
                            break;

                        case Consulta.INTERVALO_NOT_IN:
                            strCriterio.append(" NOT IN (").append(strItem.toUpperCase()).append(")");
                            break;

                        default:
                            throw new RuntimeException("Tipo de dado inválido");
                    }

                    if (blnUpper)
                    {
                        strFiltro.append(" UPPER(").append(objFiltro.getColuna()).append(")");
                    }
                    else
                    {
                        strFiltro.append(objFiltro.getColuna());
                    }
                    strFiltro.append(" ").append(strCriterio).append(" ").append(objFiltro.getConectorWhere()).append(" ");

                }
            }
            if (!itrCriteriosFiltro.hasNext() && !"".equals(strFiltro.toString()))
            {
                strFiltro.delete(strFiltro.length() - 4, strFiltro.length() - 1);
            }

            intIndice++;

        }

        if (this.strCondicaoExtra != null && !this.strCondicaoExtra.equals(""))
        {
            if (!"".equals(strFiltro.toString()))
            {
                strFiltro.append(" AND ");
            }
            strFiltro.append(this.strCondicaoExtra);
        }

        return strFiltro.toString();
    }

    /**
     * Monta o retorno na sintaxe a ser utilizada na visualização
     * 
     * @return String montada com os retornos selecionados
     */
    public void montarObjetoVisualizacao(Object objNegocio, Object objVisualizacao) throws CDException
    {
        // String para montar o retorno
        String strRetorno = "";

        // Copia objetos
        Copia.criar(objNegocio, objVisualizacao);

        // Itera filtros
        Iterator itrCamposRetorno = this.lstCamposRetorno.iterator();
        String strItem = "";
        while (itrCamposRetorno.hasNext())
        {
            strItem = (String) itrCamposRetorno.next();
            if (!"".equals(strItem))
            {
                try
                {
                    // Class clsClasse = objNegocio.getClass();
                    // Object objAtual = objNegocio;

                    Class clsClasse = objVisualizacao.getClass();
                    Object objAtual = objVisualizacao;

                    String strNomeMetodo = strItem.substring(strItem.indexOf('.') + 1);
                    strNomeMetodo += ".";
                    String strNomesMetodo[] = strNomeMetodo.split("[.]");
                    for (int i = 0; i < strNomesMetodo.length; i++)
                    {
                        clsClasse.getMethods();
                        Method mtdMetodo = null;
                        Object objRetorno = null;
                        if (clsClasse != null)
                        {
                            mtdMetodo = clsClasse.getMethod("get"
                                    + strNomesMetodo[i].substring(0, 1).toUpperCase()
                                    + strNomesMetodo[i].substring(1, strNomesMetodo[i].length()), null);
                            objRetorno = mtdMetodo.invoke(objAtual, null);
                        }
                        if (objRetorno != null)
                        {
                            if (i == strNomesMetodo.length - 1)
                            {
                                if (objRetorno instanceof Calendar)
                                {
                                    strRetorno += DataNova.format((Calendar) objRetorno);
                                }
                                else
                                {
                                    strRetorno += objRetorno;
                                }
                                strRetorno += (itrCamposRetorno.hasNext() ? ";" : "");
                            }
                            clsClasse = objRetorno.getClass();
                            objAtual = objRetorno;
                        }
                        else
                        {
                            if (mtdMetodo != null)
                            {
                                strRetorno += (itrCamposRetorno.hasNext() ? ";" : "");
                            }
                            clsClasse = null;
                            objAtual = null;
                        }
                    }
                }
                catch (NoSuchMethodException nsme)
                {
                    throw new CDException(nsme.getMessage(), nsme);
                }
                catch (IllegalAccessException iae)
                {
                    throw new CDException(iae.getMessage(), iae);
                }
                catch (InvocationTargetException ite)
                {
                    throw new CDException(ite.getMessage(), ite);
                }
            }
        }

        // Atribui o retorno ao objeto de visualização
        if (objVisualizacao instanceof Visualizacao)
        {
            Visualizacao objVisualizacaoComum = (Visualizacao) objVisualizacao;
            objVisualizacaoComum.setRetorno(strRetorno);
        }
        else
        {
            throw new CDException("O objeto de visualização deve ser uma instância da interface Visualizacao");
        }
    }

    /**
     * Adiciona campo fazer a Ordenação - Order By
     * 
     * @param strCampo
     */
    public void adicionarCampoOrdenacao(String strCampo)
    {
        this.lstCamposOrdenacao.add(strCampo);
    }

    /**
     * Monta a ordenação na sintaxe a ser utilizada nos objetos de negócio
     * 
     * @return String montada com os critérios selecionados
     */
    public String montarOrdenacao() throws CDException
    {
        // String para montar o retorno
        StringBuffer strOrdem = new StringBuffer();
        StringBuffer strRetorno = new StringBuffer();

        // Iteração
        Iterator itrCriteriosOrdem = this.lstCamposOrdenacao.iterator();
        String strItem = null;

        while (itrCriteriosOrdem.hasNext())
        {
            strItem = (String) itrCriteriosOrdem.next();
            if (!"".equals(strItem))
            {
                strOrdem.append(strItem).append(" ,");
            }
        }

        if (!"".equals(strOrdem.toString()))
        {
            // formatando a ordenação
            strOrdem.deleteCharAt(strOrdem.length() - 1);
            strRetorno.append(" ORDER BY ").append(strOrdem);
        }

        return strRetorno.toString();
    }

    /**
     * Permite adicionar uma condição extra independente para a query
     * 
     * @param strCondicaoExtra The strCondicaoExtra to set.
     */
    public void setCondicaoExtra(String strCondicaoExtra)
    {
        this.strCondicaoExtra = strCondicaoExtra;
    }

    /**
     * Permite retornar uma condição extra independente para a query
     * 
     * @return String
     */
    public String getCondicaoExtra()
    {
        return this.strCondicaoExtra;
    }

    private ConsultaComum obterInstancia() throws CDException
    {
        if (this.objClasse == null)
        {
            Object retorno = null;
            ClasseDinamica classeDinamica = new ClasseDinamica();

            if (this.strClasse == null)
            {
                throw new CDException("A classe não foi especificada");
            }

            if (this.strNomeConexao == null)
            {
                retorno = classeDinamica.obterInstancia(this.strClasse);
            }
            else
            {
                ParametroDinamico parametroDinamico = null;
                parametroDinamico = new ParametroDinamico();
                parametroDinamico.adicionarParametro(String.class, this.strNomeConexao);
                retorno = classeDinamica.obterInstancia(this.strClasse, parametroDinamico);
            }

            if (!(retorno instanceof ConsultaComum))
            {
                throw new CDException("A classe especificada não é uma instância de ConsultaComum");
            }

            this.objClasse = (ConsultaComum) retorno;
        }

        return this.objClasse;
    }
}
