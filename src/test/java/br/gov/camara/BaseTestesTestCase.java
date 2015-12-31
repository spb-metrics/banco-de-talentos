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

package br.gov.camara;

import br.gov.camara.exception.CDException;
import br.gov.camara.negocio.DAO;
import br.gov.camara.testes.base.BaseFacadeTestCase;

public abstract class BaseTestesTestCase extends BaseFacadeTestCase
{
    protected static final String MENSAGEM_INICIO_TESTE = "*** In�cio teste";
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
