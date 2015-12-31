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

    // Variáveis de instância de configuração
    private static Log log = LogFactory.getLog(TipoHTMLDAO.class);

    /**
     * Cria um novo objeto
     *
     * @param request Para obter a sessão Hibernate
     */
    public TipoMetaDadoDAO()
    {
        super("TipoMetaDadoDAO");
    }

    /**
     * Obtém todos os registros
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
     * Obtém um registro a partir da chave
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
     * @param TipoMetaDado POJO representando o objeto a ser excluído
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
        
        throw new DAOException("Método não implementado");
    }                
}
