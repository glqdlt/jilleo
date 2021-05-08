package com.glqdlt.myhome.jilleo;

/**
 * @author glqdlt
 */
public class UpdatePriceException extends RuntimeException {
    private ParserLogEntity source;
    private ParserLogEntity cursor;

    public UpdatePriceException(String message, ParserLogEntity source, ParserLogEntity cursor) {
        super(message);
        this.source = source;
        this.cursor = cursor;
    }

    public ParserLogEntity getSource() {
        return source;
    }

    public ParserLogEntity getCursor() {
        return cursor;
    }
}
