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

package br.gov.camara.negocio.comum.facade;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.gov.camara.exception.CDException;
import br.gov.camara.negocio.DAO;
import br.gov.camara.negocio.Facade;
import br.gov.camara.negocio.comum.dao.TipoMetaDadoDAO;
import br.gov.camara.negocio.exception.DAOException;

public class TipoMetaDadoFacade extends Facade
{
    private static Log log = LogFactory.getLog(TipoMetaDadoFacade.class);

    /**
     * Realiza busca de acordo com a chave informada
     *
     * @param TipoMetaDadoFacade Objetivo de chave de pesquisa
     * @return Object objeto que representa a chave da consulta
     *  
     * @throws CDException Se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public Object obterPelaChave(Object objPOJO)
        throws CDException
    {
        Object objRetorno = null;
        try 
        {
            // Instancia DAO/Bean
        	TipoMetaDadoDAO objTipoMetaDadoDAO = new TipoMetaDadoDAO();
            objRetorno = objTipoMetaDadoDAO.obterPelaChave(objPOJO);
        }
        catch(DAOException daoEx)
        {
            throw daoEx;
        }
        finally
        {
            DAO.desconectar();
        }
        return objRetorno;
    }

    /**
     * Realiza busca de todos os dados 
     *
     * @return List objeto que cont�m todos os dados da tabela
     *  
     * @throws CDException Se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public List obterTodos()
        throws CDException
    {
        List lstRetorno = null;
        try 
        {
            // Instancia DAO/Bean
        	TipoMetaDadoDAO objTipoMetaDadoDAO = new TipoMetaDadoDAO();
            lstRetorno = objTipoMetaDadoDAO.obterTodos();
        }
        catch(DAOException daoEx)
        {
            throw daoEx;
        }
        finally
        {
            DAO.desconectar();
        }
        return lstRetorno;
    }
    
}
