boxplotDual = function(eSet, boxlabels="colnames", channel="ratio", ylim=c(0,16)){
#function from arrayQualityMetrics package modified

	#getting the data
	dat = exprs(eSet)

  #importing box colors

  customcolors = brewer.pal(12,"Paired")      
  
  #saving possible labels
  poss_labels = c("colnames",varLabels(phenoData(eSet)))
  boxlabels = match.arg(boxlabels,poss_labels)
  if(boxlabels=="colnames"){
    plotnames = colnames(dat)
  }else{
    plotnames = as.character(phenoData(eSet)[[boxlabels]])
  }
  
  #How much arrays are there, what's the max.width of the labels  
  numArrays = length(plotnames)
  labelwidth = max(strwidth(plotnames,units="inches"))
  
  cex.axis = min(1,2/labelwidth)
  omi = c(1,0,0,0) 
  xaxt = "s"             
  par(omi=omi, xaxt=xaxt,pty="m",las="2",cex.axis=cex.axis)

  #Plotting labels  
  channel = match.arg(channel,c("red","green","ratio"))

  ylab = "Transformed Intensities"
	
  switch(channel,
    green = {
      boxcol = customcolors[4]
      main="Boxplot of logged green intensities"
      },
    red = {
      boxcol = customcolors[6]
      main="Boxplot of logged red intensities"
    },
		ratio = {
			boxcol = customcolors[2]
			main="Boxplot of log ratios"
		}
  )
  boxplot(dat~col(dat),names=plotnames,ylim=ylim,main=main,ylab=ylab,col=boxcol)
}