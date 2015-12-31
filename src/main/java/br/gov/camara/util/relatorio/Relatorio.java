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

package br.gov.camara.util.relatorio;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import br.gov.camara.util.relatorio.exception.RelatorioException;

/**
 * Classe para abstrair as implementa��es de frameworks de relat�rio
 *  
 */
public abstract class Relatorio
{
    protected String strLocalizacao = "";

    protected String strRelatorio = "";

    private String strTitulo = "";

    private boolean lerArquivoConfiguracao = false;

    private Map parametros = new HashMap();

    private Map subRelatorios = new HashMap();
    private boolean dirSeparatorUnixStyleOnly = true;

    public Relatorio()
    {}

    /**
     * @return Returns the lerArquivoConfiguracao.
     */
    public boolean isLerArquivoConfiguracao()
    {
        return lerArquivoConfiguracao;
    }

    /**
     * @param lerArquivoConfiguracao
     *            The lerArquivoConfiguracao to set.
     */
    public void setLerArquivoConfiguracao(boolean lerArquivoConfiguracao)
    {
        this.lerArquivoConfiguracao = lerArquivoConfiguracao;
    }

    /**
     * Obt�m o t�tulo do relat�rio
     * 
     * @return String contendo o dado
     */
    public String getTitulo()
    {
        return strTitulo;
    }

    /**
     * Preenche o t�tulo do relat�rio
     * 
     * @param strTitulo
     *            contendo o dado
     */
    public void setTitulo(String strTitulo)
    {
        this.strTitulo = strTitulo;
    }

    /**
     * Contr�i o objeto de relat�rio utilizando o nome do relat�rio a ser
     * exibido
     * 
     * @param strLocalizacao
     *            Localiza��o do relat�rio a ser trabalhado
     * @param strRelatorio
     *            Nome do relat�rio a ser trabalhado
     */
    public Relatorio(String strLocalizacao, String strRelatorio, boolean lerArquivo)
    {
        if (this.isDirSeparatorUnixStyleOnly())
            this.strLocalizacao = strLocalizacao.replace('\\', '/');
        else
            this.strLocalizacao = strLocalizacao;

        this.strRelatorio = strRelatorio;
        this.lerArquivoConfiguracao = lerArquivo;
    }

    /**
     * Exibe o relat�rio desejado em formato PDF
     * 
     * @param colDados
     *            Collection contendo os dados a serem exibidos
     */
    public abstract byte[] obterPDF(Collection colDados) throws RelatorioException;

    /**
     * @return Returns the parametros.
     */
    public Map getParametros()
    {
        return parametros;
    }

    /**
     * @param parametros
     *            The parametros to set.
     */
    public void setParametros(Map parametros)
    {
        this.parametros = parametros;
    }

    /**
     * @return Returns the subRelatorios.
     */
    public Map getSubRelatorios()
    {
        return subRelatorios;
    }

    /**
     * @param subRelatorios
     *            The subRelatorios to set.
     */
    public void setSubRelatorios(Map subRelatorios)
    {
        this.subRelatorios = subRelatorios;
    }

    /**
     * @return Returns the dirSeparatorUnixStyleOnly.
     */
    public boolean isDirSeparatorUnixStyleOnly()
    {
        return dirSeparatorUnixStyleOnly;
    }

    /**
     * @param dirSeparatorUnixStyleOnly The dirSeparatorUnixStyleOnly to set.
     */
    public void setDirSeparatorUnixStyleOnly(boolean dirSeparatorUnixStyleOnly)
    {
        this.dirSeparatorUnixStyleOnly = dirSeparatorUnixStyleOnly;
    }
}