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

package br.gov.camara.negocio.bancotalentos.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * POJO para consulta de talento
 * 
 */
public class ConsultaTalento
{
    private TipoConsultaTalento objTipoConsultaTalento;
    private CriterioConsultaTalento objCriterioConsultaTalento;
    private CategoriaTalento objCategoriaTalento;
    private List lstParametrosConsultaTalento;

    /**
     * Obtém opção de atributo de talento
     * 
     * @return objAtributoTalentoOpcao Contendo o dado
     */
    public List getListaParametrosConsulta()
    {
        return lstParametrosConsultaTalento;
    }

    /**
     * Preenche a opção de atributo de talento
     * 
     * @param AtributoTalentoOpcao Com o dado desejado
     */
    public void addParametroConsulta(CategoriaAtributoTalento categoriaAtributoTalento, AtributoTalentoOpcao atributoTalentoOpcao, String opcaoComplementar)
    {
        if (lstParametrosConsultaTalento == null)
        {
            lstParametrosConsultaTalento = new ArrayList();
        }

        ParametroConsultaTalento parametroConsultaTalento = new ParametroConsultaTalento(categoriaAtributoTalento, atributoTalentoOpcao, opcaoComplementar);
        lstParametrosConsultaTalento.add(parametroConsultaTalento);
    }

    /**
     * Obtém tipo de consulta de talento
     * 
     * @return TipoConsultaTalento Com o dado desejado
     */
    public TipoConsultaTalento getTipoConsultaTalento()
    {
        return objTipoConsultaTalento;
    }

    /**
     * Obtém critério de consulta de talento
     * 
     * @return CriterioConsultaTalento Com o dado desejado
     */
    public CriterioConsultaTalento getCriterioConsultaTalento()
    {
        return objCriterioConsultaTalento;
    }

    /**
     * Prenche o tipo de consulta de talento
     * 
     * @param objTipoConsultaTalento Contendo o dado
     */
    public void setTipoConsultaTalento(TipoConsultaTalento objTipoConsultaTalento)
    {
        this.objTipoConsultaTalento = objTipoConsultaTalento;
    }

    /**
     * Prenche o critério de consulta de talento
     * 
     * @param objCriterioConsultaTalento Contendo o dado
     */
    public void setCriterioConsultaTalento(CriterioConsultaTalento objCriterioConsultaTalento)
    {
        this.objCriterioConsultaTalento = objCriterioConsultaTalento;
    }

    /**
     * @return the objCategoriaTalento
     */
    public CategoriaTalento getCategoriaTalento()
    {
        return objCategoriaTalento;
    }

    /**
     * @param objCategoriaTalento the objCategoriaTalento to set
     */
    public void setCategoriaTalento(CategoriaTalento objCategoriaTalento)
    {
        this.objCategoriaTalento = objCategoriaTalento;
    }
}
