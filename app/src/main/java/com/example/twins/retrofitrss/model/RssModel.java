package com.example.twins.retrofitrss.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Twins on 14.09.2016.
 */
@Root(name = "rss", strict = false)
public class RssModel {
    @Element(name = "channel")
    private Channel channel;
    @Attribute
    private String version;

    public Channel getChannel ()
    {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
