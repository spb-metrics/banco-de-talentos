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

package br.gov.camara.hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.gov.camara.BaseTestesTestCase;
import br.gov.camara.negocio.exception.DAOException;

public class HibernateUtilTest extends BaseTestesTestCase
{
    /**
     * Logger for this class
     */
    private static final Log log = LogFactory.getLog(HibernateUtilTest.class);

    /**
     * O m�todo inicializar com um par�metro String utiliza as configura��es da conex�o
     * contidas no arquivo informado. A especifica��o da localiza��o do arquivo deve ser
     * relativa a um caminho que est� no classpath.
     */
    public void testInicializarString() throws Exception
    {
        log.info(MENSAGEM_INICIO_TESTE);
        // Este m�todo n�o precisa ser testado, pois a inicializa��o � feita antes de executar
        // qualquer m�todo de teste.
    }

    /**
     * Verifica se o nome do dialeto recuperado est� de acordo com 
     * aquele especificado no arquivo de configura��o para a conex�o especificada.
     */
    public void testObterDialeto()
    {
        log.info(MENSAGEM_INICIO_TESTE);
        assertEquals("org.hibernate.dialect.H2Dialect", HibernateUtilCD.obterDialeto(null));
    }

    /**
     * Verifica, atrav�s da chamada para obter a formata��o do campo data, se o dialeto
     * em quest�o est� sendo tratado pelo DAO
     */
    public void testIsDialetoTrado() throws DAOException
    {
        log.info(MENSAGEM_INICIO_TESTE);
        H2DAO h2dao = new H2DAO();
        assertFalse(h2dao.isDialetoTratado());
    }

    /**
     * O nome da conex�o padr�o � o nome do primeiro alias especificado 
     * no arquivo de configura��o.
     */
    public void testObterNomeConexaoPadrao()
    {
        log.info(MENSAGEM_INICIO_TESTE);
        assertEquals("conexaoPadrao", HibernateUtilCD.obterNomeConexaoPadrao());
    }
}
