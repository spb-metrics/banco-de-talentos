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
     * O método inicializar com um parâmetro String utiliza as configurações da conexão
     * contidas no arquivo informado. A especificação da localização do arquivo deve ser
     * relativa a um caminho que está no classpath.
     */
    public void testInicializarString() throws Exception
    {
        log.info(MENSAGEM_INICIO_TESTE);
        // Este método não precisa ser testado, pois a inicialização é feita antes de executar
        // qualquer método de teste.
    }

    /**
     * Verifica se o nome do dialeto recuperado está de acordo com 
     * aquele especificado no arquivo de configuração para a conexão especificada.
     */
    public void testObterDialeto()
    {
        log.info(MENSAGEM_INICIO_TESTE);
        assertEquals("org.hibernate.dialect.H2Dialect", HibernateUtilCD.obterDialeto(null));
    }

    /**
     * Verifica, através da chamada para obter a formatação do campo data, se o dialeto
     * em questão está sendo tratado pelo DAO
     */
    public void testIsDialetoTrado() throws DAOException
    {
        log.info(MENSAGEM_INICIO_TESTE);
        H2DAO h2dao = new H2DAO();
        assertFalse(h2dao.isDialetoTratado());
    }

    /**
     * O nome da conexão padrão é o nome do primeiro alias especificado 
     * no arquivo de configuração.
     */
    public void testObterNomeConexaoPadrao()
    {
        log.info(MENSAGEM_INICIO_TESTE);
        assertEquals("conexaoPadrao", HibernateUtilCD.obterNomeConexaoPadrao());
    }
}
