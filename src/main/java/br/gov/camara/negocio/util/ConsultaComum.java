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

package br.gov.camara.negocio.util;

import java.util.List;

import br.gov.camara.exception.CDException;

/**
 * Interface para permitir consultas genéricas para serviços
 */

public interface ConsultaComum
{
    /**
     * Define o método utilizado para efetuar consultas genéricas
     * 
     * @param objConsulta Objeto contendo a formatação da consulta
     * @param intNumeroPagina Contendo o número da página a ser exibida
     * @param intMaximoPagina Contendo o total máximo de registros a serem exibidos por página
     * 
     * @return List Contendo os POJOs com o resultado da consulta 
     */ 
    public List consultar(Consulta objConsulta, int intNumeroPagina, int intMaximoPagina) throws CDException;

    /**
     * Define o método utilizado para contar os registros daconsulta
     * 
     * @param objConsulta Objeto contendo a formatação da consulta
     * 
     * @return int Contendo o total de registros 
     */ 
    public int obterTotalRegistros(Consulta objConsulta) throws CDException;    
}
