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

import br.gov.camara.util.relatorio.exception.RelatorioException;
import br.gov.camara.util.relatorio.jasper.RelatorioJasper;

/**
 * Fábrica de relatórios
 *
 */
public class RelatorioFactory
{
    /**
     * Cria novo relatório Jasper
     * 
     * @param strLocalizacao String contendo a localização do relatório a ser criado
     * @param strRelatorio String contendo o relatório a ser criado
     * 
     * @return Relatorio Contendo a instância do objeto requerido
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
