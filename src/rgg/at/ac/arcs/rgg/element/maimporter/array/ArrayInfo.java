package at.ac.arcs.rgg.element.maimporter.array;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author ilhami
 */
public class ArrayInfo {
    private ArrayChannelInfo channelInfo;
    private ArrayColorInfo colorInfo;
    private ArrayCreator arrayCreator;
    private String arrayType;
    private int headerLineNo=1;
    private File arrayFile;
    
    public ArrayInfo(){}
    
    public ArrayInfo(ArrayChannelInfo channelInfo, ArrayColorInfo colorInfo,
            ArrayCreator arrayCreator, String arrayType,int headerLineNo){
        this.arrayCreator=arrayCreator;
        this.channelInfo=channelInfo;
        this.colorInfo=colorInfo;
        this.arrayType = arrayType;
        this.headerLineNo=headerLineNo;
    }

    public boolean isGenericType(){
      return arrayType.equals("generic");          
    }

    public File getArrayFile() {
        return arrayFile;
    }

    public void setArrayFile(File arrayFile) {
        this.arrayFile = arrayFile;
    }

    public int getHeaderLineNo() {
        return headerLineNo;
    }

    public void setHeaderLineNo(int headerLineNo) {
        this.headerLineNo = headerLineNo;
    }
    
    public ArrayCreator getArrayCreator() {
        return arrayCreator;
    }

    public void setArrayCreator(ArrayCreator arrayCreator) {
        this.arrayCreator = arrayCreator;
    }

    public ArrayChannelInfo getChannelInfo() {
        return channelInfo;
    }

    public void setChannelInfo(ArrayChannelInfo channelInfo) {
        this.channelInfo = channelInfo;
    }

    public ArrayColorInfo getColorInfo() {
        return colorInfo;
    }

    public void setColorInfo(ArrayColorInfo colorInfo) {
        this.colorInfo = colorInfo;
    }

    public String getArraySource() {
        return arrayType;
    }

    public void setArrayType(String arrayType) {
        this.arrayType = arrayType;
    }

    List<List<String>> readAssayData(int rownumber) throws IOException {
       return arrayCreator.readAssayData(arrayFile,rownumber,headerLineNo);
    }
    
}