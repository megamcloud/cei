package cn.ma.cei.model;


import cn.ma.cei.model.base.xType;
import cn.ma.cei.model.types.xBoolean;
import cn.ma.cei.model.types.xInt;
import cn.ma.cei.model.types.xString;
import cn.ma.cei.xml.XmlAnyElementTypes;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "interface")
public class xInterface {

    @XmlAttribute(name = "name")
    public String name;

    @XmlElementWrapper(name = "inputs")
    @XmlAnyElement(lax = true)
    @XmlAnyElementTypes({xString.class, xBoolean.class, xInt.class})
    public List<xType> inputList;

    @XmlElement(name = "request")
    public xRequest request;

    @XmlElement(name = "response")
    public xResponse response;
}
