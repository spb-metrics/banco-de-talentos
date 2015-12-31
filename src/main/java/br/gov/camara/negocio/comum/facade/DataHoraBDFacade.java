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
 * Facade para a exibição do currículo de determinada pessoa
 * 
 */
public class DataHoraBDFacade extends Facade
{
	// Variáveis de instância
    private static Log log = LogFactory.getLog(DataHoraBDFacade.class);
    
    private static final String nomeConexaoDataHoraBD = "conexaoDataHoraBD";

    /**
     * Obtém a data e a hora do banco de dados
     * 
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
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
