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

package br.gov.camara.util.relatorio;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import br.gov.camara.util.relatorio.exception.RelatorioException;

/**
 * Classe para abstrair as implementações de frameworks de relatório
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
     * Obtém o título do relatório
     * 
     * @return String contendo o dado
     */
    public String getTitulo()
    {
        return strTitulo;
    }

    /**
     * Preenche o título do relatório
     * 
     * @param strTitulo
     *            contendo o dado
     */
    public void setTitulo(String strTitulo)
    {
        this.strTitulo = strTitulo;
    }

    /**
     * Contrói o objeto de relatório utilizando o nome do relatório a ser
     * exibido
     * 
     * @param strLocalizacao
     *            Localização do relatório a ser trabalhado
     * @param strRelatorio
     *            Nome do relatório a ser trabalhado
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
     * Exibe o relatório desejado em formato PDF
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