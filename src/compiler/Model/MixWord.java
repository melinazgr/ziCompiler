package Model;

import java.lang.Math;

/**
 * This class is a conversion from Pascal
 * based on code from MIXBuilder Bill Menees
 * 
 * https://www.menees.com/MIXBuilder.htm
 * https://www.menees.com/Files/MIXBuilderSource.zip
 * 
 */
public class MixWord {
    public static final int BYTESIZE = 64;
    public static final int EXPBIAS = 32; // BYTESIZE / 2
    public static final int FRACBIAS = 16777216; //64^4 
    public static final int BITSPERBYTE = 6;
    public static final int NUMMASK = 0x3FFFFFFF;
    public static final int SIGNMASK = 0x40000000;

    private int m_MIXBitValue;
    // float value;
    
    public int getMixalFloat(float value) throws Exception{
        int sign, exponent;
        double fraction, logBase64;
        String errorStr;

        float oldValue = value;
        value = Math.abs(value);
        
        if(value == 0){
            m_MIXBitValue = 0;
            setField(1, 1, EXPBIAS+1);
        }
        
	    else{            

		    sign = (int)(value / Math.abs(value));
		    value = Math.abs(value);
            logBase64 = Math.log10(value) / Math.log10(BYTESIZE);
            
            //Is it an integer power?
            if (Math.floor(logBase64) == Math.ceil(logBase64)){
                exponent = (int) Math.ceil(logBase64 + 1);
            }
		    else{
                exponent = (int) Math.ceil(logBase64);
            }

		    fraction = value / Math.pow(BYTESIZE, exponent);

		    //Check the value
		    exponent = exponent + EXPBIAS;
		    errorStr = "";
            
            if ((exponent < 0) || (exponent >= BYTESIZE)){
                errorStr = "Exponent out of bounds: " + exponent + " Value: " + value;
                exponent = exponent % BYTESIZE;
            }
		
		    if ((fraction < (1.0 / BYTESIZE)) || (fraction >= 1)){
			    errorStr = "Fraction out of bounds: " + fraction + " Value: " + value;
            }
		
            //Pack the value
            setField(0, 0, sign);
            setField(1, 1, exponent);
            setField(2, 5, (int) Math.round(fraction * FRACBIAS));

            if (!errorStr.isEmpty()){
                throw new Exception(errorStr);
            }
        }

        if (oldValue < 0.0)
        {
            return -m_MIXBitValue;
        }

        return m_MIXBitValue; 
    }

    //  Returns a MIXValue that has the converted IntelValue inserted into the
    //  specified field of the passed in MIXValue parameter
    private int convertIntelToMIX(int lowField, int highField, int intelValue, int MIXValue) throws Exception{
        boolean signMatters, negative;
        int highBits, numBits, fieldMask;

        validateFieldPart(lowField, highField, 0, 5);

        signMatters = false;
        negative = false;
        if(lowField == 0){
            signMatters = true;
            negative = intelValue < 0 ? true:false;
            
            lowField++;
            intelValue = Math.abs(intelValue);
        }


        if (highField > 0){
    		//Remember that a binary MIX byte is 6 bits not 8!
            highBits = BITSPERBYTE*( 5 - highField);
            numBits = BITSPERBYTE * (highField - lowField + 1);
	
            //Chop off any bits that won't fit in the final field
            fieldMask = NUMMASK >> (BITSPERBYTE * 5 - numBits) << highBits;
            intelValue = (intelValue << highBits) & fieldMask;	

            //Zero out affected fields in Value
            MIXValue = MIXValue & (~fieldMask);			
            //Combine Value and Val
            MIXValue = MIXValue | intelValue;

        }
        
        if(signMatters){
            if(negative){
                MIXValue = MIXValue | SIGNMASK;
            }

            else{
                MIXValue = MIXValue & NUMMASK;
            }
        }
		
        return MIXValue;
    }
    
    private void validateFieldPart (int lowField, int highField, int low, int high) throws Exception{
        if ((lowField < low) || (highField > high) || (lowField > highField)){
            throw new Exception("error");
        }
    }

    private void setField( int lowField, int highField, int val)throws Exception{
	    m_MIXBitValue = convertIntelToMIX(lowField, highField, val, m_MIXBitValue);
    }    
}