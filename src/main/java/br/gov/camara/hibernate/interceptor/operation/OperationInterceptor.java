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

package br.gov.camara.hibernate.interceptor.operation;

import java.io.Serializable;
import java.util.Iterator;

import org.hibernate.type.Type;

import br.gov.camara.hibernate.exception.HibernateExceptionCD;

public interface OperationInterceptor
{
    /** @return true se foi feita alguma alteração de estado na entidade */
    public boolean executarAntesCarregar(Object entidade, Serializable id, Object[] estado, String[] nomeAtributos, Type[] tipos) throws HibernateExceptionCD;

    /** @return true se foi feita alguma alteração de estado na entidade */
    public boolean executarAntesAlterar(Object entidade, Serializable id, Object[] estadoAtual, Object[] estadoAnterior, String[] nomeAtributo, Type[] tipos) throws HibernateExceptionCD;

    /** @return true se foi feita alguma alteração de estado na entidade */
    public boolean executarAntesSalvar(Object entidade, Serializable id, Object[] estado, String[] nomeAtributos, Type[] tipos) throws HibernateExceptionCD;

    public void executarAntesExcluir(Object entidade, Serializable id, Object[] estado, String[] nomeAtributos, Type[] tipos) throws HibernateExceptionCD;

    public void executarAntesEfetivarOperacao(Iterator entities) throws HibernateExceptionCD;

    public void executarAposEfetivarOperacao(Iterator entities) throws HibernateExceptionCD;
}
