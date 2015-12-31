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

package br.gov.camara.negocio.bancotalentos.facade;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.gov.camara.exception.CDException;
import br.gov.camara.negocio.DAO;
import br.gov.camara.negocio.Facade;
import br.gov.camara.negocio.bancotalentos.dao.AtributoVirtualDAO;

/**
 * Facade para atributo de talento
 */
public class AtributoVirtualFacade extends Facade
{
    // Variáveis de instância
    private static Log log = LogFactory.getLog(AtributoVirtualFacade.class);

    /**
     * Construtor
     *
     */
    public AtributoVirtualFacade()
    {
        super();
    }

    /**
     * @param nomeConexao
     *
     */
    public AtributoVirtualFacade(String nomeConexao)
    {
        super(nomeConexao);
    }

    public List obterPorSQLParametrizado(String sql, Map parametrosConsulta) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém os registros
        AtributoVirtualDAO objAtributoVirtualDAO = new AtributoVirtualDAO(obterNomeConexao());
        List lstRetorno = null;

        try
        {
            lstRetorno = objAtributoVirtualDAO.obterPorSQLParametrizado(sql, parametrosConsulta);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar(obterNomeConexao());
        }

        return lstRetorno;
    }
}
