package cn.org.hentai.quickdao.common.exception;

/**
 * Created by matrixy on 2017/8/26.
 */
public class Problem extends RuntimeException
{
    private int errorCode = 1;
    private String errorMessage = null;

    public Problem(String message)
    {
        super(message);
        this.errorMessage = message;
    }

    public Problem(int code, String message)
    {
        super(message);
        this.errorCode = code;
        this.errorMessage = message;
    }

    public Problem(Exception ex)
    {
        super(ex);
        this.errorMessage = ex.toString();
    }

    public int getErrorCode()
    {
        return errorCode;
    }

    public void setErrorCode(int errorCode)
    {
        this.errorCode = errorCode;
    }

    public String getErrorMessage()
    {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage)
    {
        this.errorMessage = errorMessage;
    }
}
