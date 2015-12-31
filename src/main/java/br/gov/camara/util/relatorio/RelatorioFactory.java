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

import br.gov.camara.util.relatorio.exception.RelatorioException;
import br.gov.camara.util.relatorio.jasper.RelatorioJasper;

/**
 * F�brica de relat�rios
 *
 */
public class RelatorioFactory
{
    /**
     * Cria novo relat�rio Jasper
     * 
     * @param strLocalizacao String contendo a localiza��o do relat�rio a ser criado
     * @param strRelatorio String contendo o relat�rio a ser criado
     * 
     * @return Relatorio Contendo a inst�ncia do objeto requerido
     */
    public static Relatorio obterJasper(String strLocalizacao, String strRelatorio) throws RelatorioException
    {
        return new RelatorioJasper(strLocalizacao, strRelatorio, false);
    }

    /**
     * 
     * @param strLocalizacao
     * @param strRelatorio
     * @param lerArquivoConfiguracao
     * @return
     * @throws RelatorioException
     * @deprecated
     */
    public static Relatorio obterJasper(String strLocalizacao, String strRelatorio, boolean lerArquivoConfiguracao) throws RelatorioException
    {
        // return new RelatorioJasper(strLocalizacao, strRelatorio, lerArquivoConfiguracao); 
        return new RelatorioJasper(strLocalizacao, strRelatorio, false);
    }
}
