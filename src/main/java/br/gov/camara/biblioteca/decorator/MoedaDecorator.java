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

/*
 * Created on 05/05/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package br.gov.camara.biblioteca.decorator;

import org.displaytag.decorator.ColumnDecorator;

import br.gov.camara.biblioteca.util.Moeda;

/**
 * @author p_999126
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class MoedaDecorator implements ColumnDecorator
{

    public String decorate(Object columnValue)
    {
        //Declarações
        String strRetorno = null;        
        double dblValor = 0;

        if (columnValue.getClass().getName().equals("java.lang.Double"))
        {
        	dblValor = ((Double)columnValue).doubleValue();
        }
        else if (columnValue.getClass().getName().equals("java.lang.Float"))
        {
        	dblValor = ((Float)columnValue).doubleValue();
        }
        
        //Formata o valor
        strRetorno = Moeda.formatarMoeda(dblValor);
        
        return strRetorno;
    }

}
