package cn.ma.cei.model.websocket;

import cn.ma.cei.model.base.xElement;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "action")
public class xAction extends xElement {

    @XmlAttribute(name = "name")
    public String name;

    @XmlElement(name = "trigger")
    public xTrigger trigger;

    @XmlElement(name = "send")
    public xSend send;
}
