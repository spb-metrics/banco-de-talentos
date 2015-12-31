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

package sigesp.comum.util.exception;

import sigesp.comum.util.exception.SigespException;

/**
 * FabricaBeansException:
 *
 * @author André Felipe Matos de Carvalho Camara dos Deputados 01/10/2002
 * @version v 0.001
 */
public class FabricaBeansException extends SigespException
{
    /**
     * Creates a new FabricaBeansException object.
     */
    public FabricaBeansException()
    {
        super("Erro inesperado na Fábrica de Beans");
    }

    /**
     * Creates a new FabricaBeansException object.
     *
     * @param mensagem Mensagem de erro a ser exibida.
     */
    public FabricaBeansException(String mensagem)
    {
        super(mensagem);
    }
}
