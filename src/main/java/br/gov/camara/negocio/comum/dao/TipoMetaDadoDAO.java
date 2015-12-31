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

package br.gov.camara.negocio.comum.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.gov.camara.negocio.DAO;
import br.gov.camara.negocio.exception.DAOException;

/**
 * 
 * Classe DAO para a tabela TipoMetaDado
 *
 */
public class TipoMetaDadoDAO extends DAO
{

    // Vari�veis de inst�ncia de configura��o
    private static Log log = LogFactory.getLog(TipoHTMLDAO.class);

    /**
     * Cria um novo objeto
     *
     * @param request Para obter a sess�o Hibernate
     */
    public TipoMetaDadoDAO()
    {
        super("TipoMetaDadoDAO");
    }

    /**
     * Obt�m todos os registros
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws SigespDAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public List obterTodos() 
            throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }
        
        // Monta consulta
        String strConsulta =            
                " FROM" +
                " TipoMetaDado tipo" +
                " ORDER BY" +
                " tipo.descricao ASC";
        
        // Recupera os dados 
        return obter(strConsulta);
    }
        
    /**
     * Obt�m um registro a partir da chave
     *
     * @param strChave Chave do registro a ser obtido
     * 
     * @return Object POJO representando o registro obtido
     *
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public Object obterPelaChave(String strChave)
            throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }
        
        // Monta consulta
        String strConsulta =            
                " FROM" +
                " TipoMetaDado tipoMetaDado" +
                " ORDER BY" +
                " tipoMetaDado.identificador = " + strChave;
        
        // Recupera os dados 
        return obter(strConsulta);
    }    

    /**
     * Exclui um registro
     *
     * @param TipoMetaDado POJO representando o objeto a ser exclu�do
     * 
     * @return String Contendo a consulta
     * 
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public String excluirImpl(Object objTipoMetaDado)
        throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }
        
        throw new DAOException("M�todo n�o implementado");
    }                
}
