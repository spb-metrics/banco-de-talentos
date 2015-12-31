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

import java.sql.Connection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.impl.SessionImpl;

import br.gov.camara.hibernate.exception.HibernateExceptionCD;
import br.gov.camara.negocio.ParametroDAO;

class ConexaoJDBC
{
    /**
    * Logger for this class
    */
    private static final Log log = LogFactory.getLog(ConexaoJDBC.class);

    private Session sessao = null;

    ConexaoJDBC(Session sessao)
    {
        try
        {
            this.sessao = sessao;
        }
        catch (Exception e)
        {
            log.error("Ocorreu um erro recuperando a sessão hibernate: " + e.getMessage(), e);
        }
    }

    static void resolverFaltaAutocommit(String nomeConexao, Session sessaoHibernate)
    {
        try
        {
            Connection conexao = ((SessionImpl) sessaoHibernate).getJDBCContext().connection();

            if (!conexao.isClosed() && !conexao.getAutoCommit())
            {
                conexao.commit();
            }
        }
        catch (Throwable sqle)
        {
            throw new HibernateExceptionCD(sqle);
        }
    }

    int executar(String comandoSql)
    {
        return executarComParametros(comandoSql, null);
    }

    int executarComParametros(String comandoSql, ParametroDAO parametros)
    {
        Query querySql = criarQuerySQL(comandoSql);

        if (parametros != null)
        {
            querySql = parametros.setarParametroQuery(querySql);
        }

        return querySql.executeUpdate();
    }

    List recuperar(String comandoSql)
    {
        return recuperarComParametros(comandoSql, null);
    }

    List recuperarComParametros(String comandoSql, ParametroDAO parametros)
    {
        Query querySql = criarQuerySQL(comandoSql);

        if (parametros != null)
        {
            querySql = parametros.setarParametroQuery(querySql);
        }

        return querySql.list();
    }

    private Query criarQuerySQL(String comandoSql)
    {
        Query querySql = sessao.createSQLQuery(comandoSql);
        querySql.setResultTransformer(AliasLowerToMapResultTransformer.INSTANCE);
        return querySql;
    }
}
