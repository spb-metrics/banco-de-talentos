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

package br.gov.camara;

import br.gov.camara.exception.CDException;
import br.gov.camara.negocio.DAO;
import br.gov.camara.testes.base.BaseFacadeTestCase;

public abstract class BaseTestesTestCase extends BaseFacadeTestCase
{
    protected static final String MENSAGEM_INICIO_TESTE = "*** Início teste";
    protected static final String ARQUIVO_HIBERNATE_CONFIG = "Testes-hibernate.xml";
    
    public BaseTestesTestCase() {
    	try
    	{
    		inicializarHibernate(ARQUIVO_HIBERNATE_CONFIG);
    	}
    	catch (CDException e)
    	{
    		e.printStackTrace();
    		throw new RuntimeException(e);
    	}
	}

	protected void executarAntesDeChamarMetodo() throws CDException
    {
		DAO.iniciarTransacao();
    }

    protected void executarDepoisDeChamarMetodo() throws CDException
    {
    	DAO.desfazerTransacao();
    }
    
    @Override
    protected void finalize() throws Throwable {
        finalizarHibernate();
    	super.finalize();
    }
}
