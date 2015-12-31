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

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.gov.camara.exception.CDException;
import br.gov.camara.negocio.DAO;
import br.gov.camara.negocio.Facade;
import br.gov.camara.negocio.comum.dao.DataDAO;
import br.gov.camara.negocio.exception.DAOException;

/**
 * Facade para a exibi��o do curr�culo de determinada pessoa
 * 
 */
public class DataHoraBDFacade extends Facade
{
	// Vari�veis de inst�ncia
    private static Log log = LogFactory.getLog(DataHoraBDFacade.class);
    
    private static final String nomeConexaoDataHoraBD = "conexaoDataHoraBD";

    /**
     * Obt�m a data e a hora do banco de dados
     * 
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
	 */
	public static Date obterTimeStampBD()
			throws CDException
	{
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        DataDAO dataBD = new DataDAO(nomeConexaoDataHoraBD);
        Date objTimeStamp = null;
        
		try
		{
            objTimeStamp = dataBD.obterTimeStamp();
		}
		catch (Exception daoe)
		{
		    throw new CDException(daoe);
		}
		finally
		{
			DAO.desconectar(nomeConexaoDataHoraBD);
		}
        
		return objTimeStamp;
	}
}
