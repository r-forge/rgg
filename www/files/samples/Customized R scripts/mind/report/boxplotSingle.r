library(RColorBrewer)
library(Biobase)

################################################################################
#boxplotSingle - function for creating a boxplot for each sample of the specified expressionSet, 
#coloring it with channel argument("green" or "red"), labeling with boxlabels("colnames" or 
#phenoData column name), and scaling the y-axis.
################################################################################
boxplotSingle = function(eset,ylim=c(0,16), boxlabels="colnames", channel="green", do.logtransform = FALSE){
#function from arrayQualityMetrics package modified
  customcolors = brewer.pal(12,"Paired")
  
  #getting data
  dat = exprs(eset)
  
  #if specified, logtransform
  if(do.logtransform){
    dat = log2(dat)
    dat[dat == -Inf] <- NA
  }         
  
  #creating vector of possible lables
  poss_labels = c("colnames",varLabels(phenoData(eset)))
	
	#is the specified label a possible one
  boxlabels = match.arg(boxlabels,poss_labels)
	
  if(boxlabels=="colnames"){
    plotnames = colnames(dat)
  }else{
    plotnames = as.character(phenoData(data.eSet)[[boxlabels]])
  }
  
  #How much arrays are there, what's the max.width of the labels  
  numArrays = length(plotnames)
  labelwidth = max(strwidth(plotnames,units="inches"))
  
	#scaling the labels to 2 inch if they are too long
  cex.axis = min(1,2/labelwidth)
  omi = c(max(labelwidth/2,.5),0,0,0) 
  xaxt = "s"             
  par(omi=omi, xaxt=xaxt,pty="m",las="2",cex.axis=cex.axis)

  #y-labels  
  if(do.logtransform){
    ylab = "Log2-transformed intensities"
  }else{
    ylab = "Intensities"
  }
	
	#setting boxcolor
	channel = match.arg(channel,c("red","green"))
  switch(channel,
    green = {
      boxcol = customcolors[4]
      main="Boxplot of green intensities"
      },
    red = {
      boxcol = customcolors[6]
      main="Boxplot of red intensities"
    }
  )
	
  boxplot(dat~col(dat),names=plotnames,ylim=ylim,main=main,ylab=ylab,col=boxcol)
}