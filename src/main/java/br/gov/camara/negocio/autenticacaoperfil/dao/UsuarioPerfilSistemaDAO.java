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

package br.gov.camara.negocio.autenticacaoperfil.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.gov.camara.negocio.DAO;
import br.gov.camara.negocio.autenticacaoperfil.pojo.UsuarioPerfilSistema;
import br.gov.camara.negocio.autenticacaoperfil.pojo.UsuarioSistema;
import br.gov.camara.negocio.exception.DAOException;

/**
 * 
 * Classe DAO para perfil de sistema
 *
 */

public class UsuarioPerfilSistemaDAO extends DAO
{
    // Inicializa o log
    private static Log log = LogFactory.getLog(DAO.class);

    /**
     * Construtor sem argumentos
     *
     */
    public UsuarioPerfilSistemaDAO()
    {
        super("Usuário Perfil Sistema");
    }

    public UsuarioPerfilSistemaDAO(String nomeConexao)
    {
        super(nomeConexao);
    }

    /**
     * Obtém todos os registros
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public List obterTodos() throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" UsuarioPerfilSistema usuarioPerfilSistema");
        strbConsulta.append(" ORDER BY");
        strbConsulta.append(" usuarioPerfilSistema.usuarioSistema.nome ASC");
        
        // Retorna
        return obter(strbConsulta.toString()); 
    }

    /**
     * Obtém um registro a partir da chave
     *
     * @param strChave Chave do registro a ser obtido
     * 
     * @return Object POJO representando o registro obtido
     *
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public Object obterPelaChave(UsuarioPerfilSistema objUsuarioPerfilSistema) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }
        
        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" UsuarioPerfilSistema usuarioPerfilSistema");
        strbConsulta.append(" WHERE");
        strbConsulta.append(" usuarioPerfilSistema.usuarioSistema.identificador = ");
        strbConsulta.append(objUsuarioPerfilSistema.getUsuarioSistema().getIdentificador().intValue());
        strbConsulta.append(" AND usuarioPerfilSistema.perfilSistema.identificador = ");
        strbConsulta.append(objUsuarioPerfilSistema.getPerfilSistema().getIdentificador().intValue());
            
        // Verifica se há retorno
        List lstResultado = obter(strbConsulta.toString());
        if (!lstResultado.isEmpty())
            return (UsuarioPerfilSistema) lstResultado.get(0);
        else
        {
            return null;
        }
    }

    /**
     * Obtém um registro a partir do objeto controlado
     *
     * @param strObjetoControlado Objeto controlado do registro a ser obtido
     * 
     * @return Object POJO representando o registro obtido
     *
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public List obterPeloUsuarioSistema(UsuarioPerfilSistema objUsuarioPerfilSistema) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }
        
        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" UsuarioPerfilSistema usuarioPerfilSistema");
        strbConsulta.append(" WHERE");
        strbConsulta.append(" usuarioPerfilSistema.usuarioSistema.identificador = ");
        strbConsulta.append(objUsuarioPerfilSistema.getUsuarioSistema().getIdentificador().intValue());
        
        return obter(strbConsulta.toString());
    }
    
    /**
     * Obtém um registro a partir do objeto controlado
     *
     * @param strObjetoControlado Objeto controlado do registro a ser obtido
     * 
     * @return Object POJO representando o registro obtido
     *
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public List obterPeloPerfilSistema(UsuarioPerfilSistema objUsuarioPerfilSistema) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }
        
        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" UsuarioPerfilSistema usuarioPerfilSistema");
        strbConsulta.append(" WHERE");
        strbConsulta.append(" usuarioPerfilSistema.perfilSistema.identificador = ");
        strbConsulta.append(objUsuarioPerfilSistema.getPerfilSistema().getIdentificador().intValue());
        strbConsulta.append(" ORDER BY usuarioPerfilSistema.usuarioSistema.nome");
        
        return obter(strbConsulta.toString());
    }

    /**
     * Exclui um registro
     *
     * @param Object POJO representando o objeto a ser excluído
     * 
     * @return String Contendo a consulta de exclusão 
     * 
     * @throws DAOException Se ocorrer algum erro
     * 
     */
    protected String excluirImpl(Object objPOJO) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }
        
        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" UsuarioPerfilSistema usuarioPerfilSistema");
        strbConsulta.append(" WHERE");
        strbConsulta.append(" usuarioPerfilSistema.usuarioSistema.identificador = ");
        strbConsulta.append(((UsuarioPerfilSistema) objPOJO).getUsuarioSistema().getIdentificador().intValue());
        if (((UsuarioPerfilSistema) objPOJO).getPerfilSistema() != null)
        {
            strbConsulta.append(" AND usuarioPerfilSistema.perfilSistema.identificador = ");
            strbConsulta.append(((UsuarioPerfilSistema) objPOJO).getPerfilSistema().getIdentificador().intValue());
        }
        return strbConsulta.toString();
    }

    /**
     * Método que pede a inicialização da propriedade Gestores, que é lazy
     * 
     * @param UsuarioPerfilSistemaDTO Classe que tem o atributo que vai ser recuperado
     * 
     * @see br.gov.camara.negocio.DAO#inicializarRelacionamento(java.lang.Object)
     * 
     * @throws DAOException Se ocorrer algum
     * 
     */
    public void inicializarUsuarioSistema(UsuarioPerfilSistema objUsuarioPerfilSistema) throws DAOException
    {
        inicializarRelacionamento(objUsuarioPerfilSistema.getUsuarioSistema());
    }

    /**
     * Método que pede a inicialização da propriedade EstruturaCEUnidade, que é lazy
     * 
     * @param List Lista de objetos do tipo CargoEfetivo
     * 
     * @see br.gov.camara.negocio.DAO#inicializarRelacionamento(java.lang.Object)
     * 
     * @throws DAOException Se ocorrer algum erro
     * 
     */
    public void inicializarUsuarioSistema(List lstUsuarioPerfilSistema) throws DAOException
    {

        for (int i = 0; i < lstUsuarioPerfilSistema.size(); i++)
        {
            inicializarUsuarioSistema((UsuarioPerfilSistema) lstUsuarioPerfilSistema.get(i));
        }
    }
    
    /**
     * Método que pede a inicialização da propriedade Gestores, que é lazy
     * 
     * @param UsuarioPerfilSistemaDTO Classe que tem o atributo que vai ser recuperado
     * 
     * @see br.gov.camara.negocio.DAO#inicializarRelacionamento(java.lang.Object)
     * 
     * @throws DAOException Se ocorrer algum
     * 
     */
    public void inicializarPerfilSistema(UsuarioPerfilSistema objUsuarioPerfilSistema) throws DAOException
    {
        inicializarRelacionamento(objUsuarioPerfilSistema.getPerfilSistema());
        inicializarRelacionamento(objUsuarioPerfilSistema.getPerfilSistema().getSistema());
    }

    /**
     * Método que pede a inicialização da propriedade EstruturaCEUnidade, que é lazy
     * 
     * @param List Lista de objetos do tipo CargoEfetivo
     * 
     * @see br.gov.camara.negocio.DAO#inicializarRelacionamento(java.lang.Object)
     * 
     * @throws DAOException Se ocorrer algum erro
     * 
     */
    public void inicializarPerfilSistema(List lstUsuarioPerfilSistema) throws DAOException
    {

        for (int i = 0; i < lstUsuarioPerfilSistema.size(); i++)
        {
            inicializarPerfilSistema((UsuarioPerfilSistema) lstUsuarioPerfilSistema.get(i));
        }
    }
    
    
    public void excluirPerfil(Integer intUsuarioLogado, Integer intUsuarioDestino, Integer intSistema) 
    	throws DAOException
    {
    	// SQL que realizar a cópia dos perfis que possuem ao usuário origem para o usuário destino
    	// Deve-se observar que só serão copiados os perfis que o usuário logado seja gestor ou 
    	// que o usuário seja gestor do sistema
    	
        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" DELETE FROM USUARIOPERFILSISTEMA");
        strbConsulta.append(" WHERE IDEPERFILSISTEMA IN");
        strbConsulta.append(" ( SELECT IDEPERFILSISTEMA ");
        strbConsulta.append("   FROM PERFILSISTEMA");
        strbConsulta.append("   WHERE IDESISTEMA = ");
        strbConsulta.append(    intSistema);
        strbConsulta.append(" )");
        strbConsulta.append(" AND IDEUSUARIO = ");
        strbConsulta.append(  intUsuarioDestino);
        strbConsulta.append(" AND (");
        strbConsulta.append("       IDEPERFILSISTEMA IN");
        strbConsulta.append("       (");
        strbConsulta.append("           SELECT B.IDEPERFILSISTEMA");
        strbConsulta.append("           FROM GESTORSISTEMA A, PERFILSISTEMA B");
        strbConsulta.append("           WHERE A.IDESISTEMA = ");
        strbConsulta.append(            intSistema);
        strbConsulta.append("           AND A.IDESISTEMA = B.IDESISTEMA");
        strbConsulta.append("           AND A.IDEUSUARIO = ");
        strbConsulta.append(            intUsuarioLogado);
        strbConsulta.append("       )");
        strbConsulta.append("       OR");
        strbConsulta.append("       IDEPERFILSISTEMA IN");
        strbConsulta.append("       (");
        strbConsulta.append("           SELECT IDEPERFILSISTEMA");
        strbConsulta.append("           FROM USUARIOPERFILSISTEMA");
        strbConsulta.append("           WHERE IDEUSUARIO = ");
        strbConsulta.append(            intUsuarioLogado);
        strbConsulta.append("           AND INDGESTORPERFIL = 'S'");
        strbConsulta.append("       )");
        strbConsulta.append("   )");
        
    	executarSQL(strbConsulta.toString());
    }
    
    public void  realizarCopia(Integer intUsuarioLogado, Integer intUsuarioOrigem, Integer intUsuarioDestino, 
    		Integer intSistema, String strDatInicioValidade, String strDatTerminoValidade) 
    	throws DAOException
    {
    	// SQL que realizar a cópia dos perfis que possuem ao usuário origem para o usuário destino
    	// Deve-se observar que só serão copiados os perfis que o usuário logado seja gestor ou 
    	// que o usuário seja gestor do sistema
    	
        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" INSERT INTO USUARIOPERFILSISTEMA");
        strbConsulta.append(" (SELECT ");
        strbConsulta.append( intUsuarioDestino.intValue());
        strbConsulta.append(" AS IDEUSUARIO, A.IDEPERFILSISTEMA, 'N', ");
        strbConsulta.append(  intUsuarioLogado.intValue());
        strbConsulta.append(" , ");
        strbConsulta.append(  obterCampoDataAtual());
        strbConsulta.append(" ,");
        strbConsulta.append(  ("".equals(strDatInicioValidade)?null:("'"+strDatInicioValidade+"'")));
        strbConsulta.append(" ,");
        strbConsulta.append(  ("".equals(strDatTerminoValidade)?null:("'"+strDatTerminoValidade+"'")));
        strbConsulta.append(" FROM USUARIOPERFILSISTEMA A, PERFILSISTEMA B");
        strbConsulta.append(" WHERE A.IDEPERFILSISTEMA = B.IDEPERFILSISTEMA");
        strbConsulta.append(" AND B.IDESISTEMA = ");
        strbConsulta.append(intSistema);
        strbConsulta.append(" AND A.IDEUSUARIO = ");
        strbConsulta.append(intUsuarioOrigem);
        strbConsulta.append(" AND (");
        strbConsulta.append("       B.IDESISTEMA IN");
        strbConsulta.append("       (");
        strbConsulta.append("           SELECT IDESISTEMA");
        strbConsulta.append("           FROM GESTORSISTEMA");
        strbConsulta.append("           WHERE IDESISTEMA = ");
        strbConsulta.append(            intSistema);
        strbConsulta.append("           AND IDEUSUARIO = ");
        strbConsulta.append(            intUsuarioLogado);
        strbConsulta.append("       )");
        strbConsulta.append("       OR A.IDEPERFILSISTEMA IN");
        strbConsulta.append("       (");
        strbConsulta.append("           SELECT IDEPERFILSISTEMA");
        strbConsulta.append("           FROM USUARIOPERFILSISTEMA");
        strbConsulta.append("           WHERE IDEUSUARIO = ");
        strbConsulta.append(            intUsuarioLogado);
        strbConsulta.append("           AND INDGESTORPERFIL = 'S'");
        strbConsulta.append("       )");
        strbConsulta.append("     )");
        strbConsulta.append(" AND A.IDEPERFILSISTEMA NOT IN");
        strbConsulta.append("   (");
        strbConsulta.append("       SELECT IDEPERFILSISTEMA");
        strbConsulta.append("       FROM USUARIOPERFILSISTEMA B");
        strbConsulta.append("       WHERE B.IDEUSUARIO = ");
        strbConsulta.append(        intUsuarioDestino);
        strbConsulta.append("   )");
        strbConsulta.append(" )");
    	executarSQL(strbConsulta.toString());
    }

    /**
     * Obtém um registro a partir da chave
     *
     * @param strChave Chave do registro a ser obtido
     * 
     * @return Object POJO representando o registro obtido
     *
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public boolean isGestorPerfil(UsuarioPerfilSistema objUsuarioPerfilSistema) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }
        
        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" UsuarioPerfilSistema usuarioPerfilSistema");
        strbConsulta.append(" WHERE");
        strbConsulta.append(" usuarioPerfilSistema.usuarioSistema.identificador = ");
        strbConsulta.append(objUsuarioPerfilSistema.getUsuarioSistema().getIdentificador().intValue());
        strbConsulta.append(" AND usuarioPerfilSistema.perfilSistema.identificador = ");
        strbConsulta.append(objUsuarioPerfilSistema.getPerfilSistema().getIdentificador().intValue());
        strbConsulta.append(" AND usuarioPerfilSistema.indicativoGestor = 'S'");
            
        // Verifica se há retorno
        List lstResultado = obter(strbConsulta.toString());
        return  (!lstResultado.isEmpty());
    }
    
    /**
     * Obtém um registro a partir da chave
     *
     * @param strChave Chave do registro a ser obtido
     * 
     * @return Object POJO representando o registro obtido
     *
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public boolean isGestorDeAlgumPerfil(UsuarioSistema objUsuarioSistema) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }
        
        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" UsuarioPerfilSistema usuarioPerfilSistema");
        strbConsulta.append(" WHERE");
        strbConsulta.append(" usuarioPerfilSistema.usuarioSistema.identificador = ");
        strbConsulta.append(objUsuarioSistema.getIdentificador().intValue());
        strbConsulta.append(" AND usuarioPerfilSistema.indicativoGestor = 'S'");
        
        // Verifica se há retorno
        List lstResultado = obter(strbConsulta.toString());
        return  (!lstResultado.isEmpty());
    }

    public List obterGestoresPerfil() throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" UsuarioPerfilSistema usuarioPerfilSistema");
        strbConsulta.append(" WHERE");
        strbConsulta.append(" usuarioPerfilSistema.indicativoGestor = 'S'");
        
        // Verifica se há retorno
        return obter(strbConsulta.toString());
    }
    
    public List obterPeloObjetoControlado(String strObjControlado) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" SELECT usuarioPerfilSistema");
        strbConsulta.append(" FROM");
        strbConsulta.append(" UsuarioPerfilSistema usuarioPerfilSistema");
        strbConsulta.append(" JOIN usuarioPerfilSistema.perfilSistema perfilSistema ");
        strbConsulta.append(" JOIN perfilSistema.funcionalidades funcionalidade");
        strbConsulta.append(" WHERE");
        strbConsulta.append(" funcionalidade.objetoControlado = '");
        strbConsulta.append(strObjControlado);
        strbConsulta.append("'");

        // Verifica se há retorno
        return obter(strbConsulta.toString());
    }
}
