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
    // Vari�veis de inst�ncia
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

        // Instancia DAO e obt�m os registros
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
