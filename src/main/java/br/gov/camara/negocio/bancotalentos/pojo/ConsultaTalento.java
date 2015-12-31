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
     * Obt�m op��o de atributo de talento
     * 
     * @return objAtributoTalentoOpcao Contendo o dado
     */
    public List getListaParametrosConsulta()
    {
        return lstParametrosConsultaTalento;
    }

    /**
     * Preenche a op��o de atributo de talento
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
     * Obt�m tipo de consulta de talento
     * 
     * @return TipoConsultaTalento Com o dado desejado
     */
    public TipoConsultaTalento getTipoConsultaTalento()
    {
        return objTipoConsultaTalento;
    }

    /**
     * Obt�m crit�rio de consulta de talento
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
     * Prenche o crit�rio de consulta de talento
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
