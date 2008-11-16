createReport2color = function(MA,normMA,data.eSet,data.normalized.eSet,rgghome,reportfolder=paste(rgghome,"reportoutput",sep="/"),target,bgCorrMethod,bgCorrOffset,normWithinMethod,normBetweenMethod,heatcov,boxlabels="colnames",pcncomp=3,pcgroup="none",trans="log2",maplots=TRUE){
	dir.create(reportfolder)
	file.copy(stylesheet,paste(reportfolder,"style.css",sep="/"), overwrite = T)
	
	#if running in non-interactive mode (e.g. in RGGRunner)
	if(!dev.interactive()){		
		postscript(file="temp.eps")
		dev.control(displaylist='enable')	
	}
	
  nrppi = 72
  w=8
  h=8
  wd = w*nrppi
  hg = h*nrppi
  
  reportfile = paste(reportfolder,"/MIND-report",sep="")
  report = openHtmlPage(reportfile,"MIND-report - dual color data")
  writeLines("<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\">",report)
  writeLines("<center>",report)
  writeLines("<h1>MIND-report</h1>",report)
  writeLines("<h3>for dual-color data</h3>",report)
  writeLines(paste("created",date(),"by MIND GUI"),report)
  writeLines("</center>",report)
  
  
  writeLines("<table border=\"0\" align=\"center\">",report)
  writeLines("<tr class=\"sectionheader\"><th>Data preparation parameter</th><th>Settings</th></tr>",report)
  writeLines(sprintf("<tr><td>Targetfile path:</td><td>%s</td></tr>",target),report)
  writeLines(sprintf("<tr><td>Background correction:</td><td>%s</td></tr>",bgCorrMethod),report)  
  writeLines(sprintf("<tr><td>Offset:</td><td>%s</td></tr>",bgCorrOffset),report)
  writeLines(sprintf("<tr><td>Transformation:</td><td>%s</td></tr>",trans),report)
	writeLines(sprintf("<tr><td>Normalization within arrays:</td><td>%s</td></tr>",normWithinMethod),report)
  writeLines(sprintf("<tr><td>Normalization between arrays:</td><td>%s</td></tr>",normBetweenMethod),report)
  writeLines("</table>",report)
  
  
  writeLines("<table border=\"0\" align=\"center\">",report)
  
  writeLines("<tr><th colspan=\"2\"><h1>QC plots</h1></th></tr>",report)  
  
	nsteps = ifelse(maplots,7+ncol(data.eSet)[[1]]*2,7)
	
	pbar = winProgressBar(title = "MIND-report - dual color data", label = "Report creation: 0% done.", min = 0, max = nsteps, initial = 0, width = 300)
	j=0
  
  #BOXPLOTS
  if(boxlabels == "colnames"){
    writeLines("<tr><th class=\"sectionheader\" colspan=\"2\"><h2>Boxplot</h2></th></tr>",report)
  }else{
    writeLines(sprintf("<tr><th class=\"sectionheader\" colspan=\"2\"><h2>Boxplots - labeled with covariate: %s</h2></th></tr>",boxlabels),report)
  }

  
  #creating boxplots
	beforerangeM = range(exprs(data.eSet),na.rm=T,finite=T)
  afterrangeM = range(exprs(data.normalized.eSet),na.rm=T,finite=T)
  rangeM = c(min(beforerangeM[1],afterrangeM[1]),max(beforerangeM[2],afterrangeM[2]))
		
	writeLines(sprintf("<tr class=\"sectionheader\"><th>M-values before normalization</th><th>M-values after normalization with: %s and %s </th></tr>",normWithinMethod,normBetweenMethod),report)
  names = paste(reportfolder,"/","boxplotMbefore",c(".pdf",".png"),sep="")
    
  boxplotDual(data.eSet, boxlabels=boxlabels, channel="ratio",ylim=rangeM)
  dev.print(device=pdf,names[1],width=w,height=h,pointsize=14)
  dev.print(device=png,names[2],width=wd,height=hg,pointsize=10)
  if(dev.interactive()){
	 dev.off()
  }
	
  j=j+1
	setWinProgressBar(pbar, j, label=paste("Report creation:",round(j/nsteps*100),"% done."))  
  
  names = paste(reportfolder,"/","boxplotMafter",c(".pdf",".png"),sep="")

  boxplotDual(data.normalized.eSet, boxlabels=boxlabels, channel="ratio",ylim=rangeM)
  dev.print(device=pdf,names[1],width=w,height=h,pointsize=14)
  dev.print(device=png,names[2],width=wd,height=hg,pointsize=10)
  if(dev.interactive()){
	 dev.off()
  }
  
  j=j+1
	setWinProgressBar(pbar, j, label=paste("Report creation:",round(j/nsteps*100),"% done."))
  
  writeLines("<tr><td><a href=\"boxplotMbefore.pdf\"><img src=\"boxplotMbefore.png\"/></a></td><td><a href=\"boxplotMafter.pdf\"><img src=\"boxplotMafter.png\"/></a></td></tr>",report)
  
  #MA PLOTS
  writeLines("<tr><th class=\"sectionheader\" colspan=\"2\"><h2>MA plots (for each array)</h2></th></tr>",report)
  if(maplots){
  writeLines("<tr><td colspan=\"2\"><center><a href=\"maplots.html\">Link to MAplots</a></center></td></tr>",report)
  }else{
  writeLines("<tr><td colspan=\"2\"><center>none</center></td></tr>",report)
  }
  
  #HEATMAPS
  writeLines(sprintf("<tr><th class=\"sectionheader\" colspan=\"2\"><h2>Heatmap (color bars correspond to covariate: %s)</h2></th></tr>",heatcov),report)
  writeLines(sprintf("<tr class=\"sectionheader\"><th>Before normalization</th><th>After normalization with: %s and %s</th></tr>",normWithinMethod,normBetweenMethod),report) 
  
  #creating heatmaps
  names = paste(reportfolder,"/","heatmapbefore",c(".pdf",".png"),sep="")

  doHeatmap(data.eSet,heatcov)
  dev.print(device=pdf,names[1],width=w,height=h,pointsize=14)
  dev.print(device=png,names[2],width=wd,height=hg,pointsize=10)
  if(dev.interactive()){
	 dev.off()
  }
  
	j=j+1
	setWinProgressBar(pbar, j, label=paste("Report creation:",round(j/nsteps*100),"% done."))
	
  names = paste(reportfolder,"/","heatmapafter",c(".pdf",".png"),sep="")

  doHeatmap(data.normalized.eSet,heatcov)
  dev.print(device=pdf,names[1],width=w,height=h,pointsize=14)
  dev.print(device=png,names[2],width=wd,height=hg,pointsize=10)
  if(dev.interactive()){
	 dev.off()
  }
  
	j=j+1
	setWinProgressBar(pbar, j, label=paste("Report creation:",round(j/nsteps*100),"% done."))
	
  writeLines("<tr><td><a href=\"heatmapbefore.pdf\"><img src=\"heatmapbefore.png\"/></a></td><td><a href=\"heatmapafter.pdf\"><img src=\"heatmapafter.png\"/></a></td></tr>",report)
  
  #MEANSDPLOTS
  writeLines("<tr><th class=\"sectionheader\" colspan=\"2\"><h2>Variance-Mean-dependency</h2></th></tr>",report)
  writeLines(sprintf("<tr class=\"sectionheader\"><th>Before normalization</th><th>After normalization with: %s and %s </th></tr>",normWithinMethod,normBetweenMethod),report)   
  #creating meansdplots
  names = paste(reportfolder,"/","meansdbefore",c(".pdf",".png"),sep="")

  plotmeanSd(data.eSet)
  dev.print(device=pdf,names[1],width=w,height=h,pointsize=14)
  dev.print(device=png,names[2],width=wd,height=hg,pointsize=10)
  if(dev.interactive()){
	 dev.off()
  }
  
	j=j+1
	setWinProgressBar(pbar, j, label=paste("Report creation:",round(j/nsteps*100),"% done."))
	
  names = paste(reportfolder,"/","meansdafter",c(".pdf",".png"),sep="")

  plotmeanSd(data.normalized.eSet)
  dev.print(device=pdf,names[1],width=w,height=h,pointsize=14)
  dev.print(device=png,names[2],width=wd,height=hg,pointsize=10)
  if(dev.interactive()){
	 dev.off()
  }
  
  writeLines("<tr><td><a href=\"meansdbefore.pdf\"><img src=\"meansdbefore.png\"/></a></td><td><a href=\"meansdafter.pdf\"><img src=\"meansdafter.png\"/></a></td></tr>",report)
  
  #PRINCIPAL COMPONENTS PLOTS
  writeLines(sprintf("<tr><th class=\"sectionheader\" colspan=\"2\"><h2>Principal components (first %s) - colored with covariate: %s</h2></th></tr>",pcncomp,pcgroup),report)
  writeLines(sprintf("<tr class=\"sectionheader\"><th>Before normalization</th><th>After normalization with: %s and %s </th></tr>",normWithinMethod,normBetweenMethod),report) 
  #creating principal components plot
  names = paste(reportfolder,"/","pcompbefore",c(".pdf",".png"),sep="")

  prcompPlot(data.eSet,pcgroup,pcncomp)
  dev.print(device=pdf,names[1],width=w,height=h,pointsize=14)
  dev.print(device=png,names[2],width=wd,height=hg,pointsize=10)
  if(dev.interactive()){
	 dev.off()
  }
  
  j=j+1
	setWinProgressBar(pbar, j, label=paste("Report creation:",round(j/nsteps*100),"% done."))
	
  names = paste(reportfolder,"/","pcompafter",c(".pdf",".png"),sep="")

  prcompPlot(data.normalized.eSet,pcgroup,pcncomp)
  dev.print(device=pdf,names[1],width=w,height=h,pointsize=14)
  dev.print(device=png,names[2],width=wd,height=hg,pointsize=10)
  if(dev.interactive()){
	 dev.off()
  }
  
	j=j+1
	setWinProgressBar(pbar, j, label=paste("Report creation:",round(j/nsteps*100),"% done."))
  
	writeLines("<tr><td><a href=\"pcompbefore.pdf\"><img src=\"pcompbefore.png\"/></a></td><td><a href=\"pcompafter.pdf\"><img src=\"pcompafter.png\"/></a></td></tr>",report)

  closeHtmlPage(report)
  
  maplotfile = paste(reportfolder,"/maplots",sep="")
  ma = openHtmlPage(maplotfile,"MIND-report - dual color data")
  writeLines("<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\">",ma)
  if(maplots){
  #MA PLOTS
  writeLines("<center><a href=\"MIND-report.html\">back</a></center>",ma)
  writeLines("<table border=\"0\" align=\"center\">",ma)
  writeLines("<tr><th class=\"sectionheader\" colspan=\"3\"><h2>MA plots (for each array)</h2></th></tr>",ma)
  writeLines(sprintf("<tr class=\"sectionheader\"><th></th><th>Before normalization</th><th>After normalization with: %s and %s</th></tr>",normWithinMethod,normBetweenMethod),ma) 
  dir.create(paste(reportfolder,"/MAplots",sep=""))
  for (i in 1:ncol(exprs(data.eSet))){
    relnamesb = paste("MAplots/","maplot",i,"before",c(".pdf",".png"),sep="")
    namesbefore = paste(reportfolder,"/",relnamesb,sep="")

	plotMADual(MA,i)
    dev.print(device=pdf,namesbefore[1],width=w,height=h,pointsize=14)
    dev.print(device=png,namesbefore[2],width=wd,height=hg,pointsize=10)
    if(dev.interactive()){
	 dev.off()
	}
	
    j=j+1
		setWinProgressBar(pbar, j, label=paste("Report creation:",round(j/nsteps*100),"% done."))
	
    relnamesa = paste("MAplots/","maplot",i,"after",c(".pdf",".png"),sep="")
    namesafter = paste(reportfolder,"/",relnamesa,sep="")

	plotMADual(normMA,i)
    dev.print(device=pdf,namesafter[1],width=w,height=h,pointsize=14)
    dev.print(device=png,namesafter[2],width=wd,height=hg,pointsize=10)
    if(dev.interactive()){
	 dev.off()
	}
		
	  j=j+1
		setWinProgressBar(pbar, j, label=paste("Report creation:",round(j/nsteps*100),"% done."))
	
    writeLines(sprintf("<tr><td>%s</td><td><a link href=\"%s\"><img src=\"%s\"/></a></td><td><a link href=\"%s\"><img src=\"%s\"/></a></td></tr>",i,relnamesb[1],relnamesb[2],relnamesa[1],relnamesa[2]),ma)
    }
  
  writeLines("</table>",ma)
  closeHtmlPage(ma)
	}            
	close(pbar)
	
	if(!dev.interactive()){
		dev.off()
		file.remove("temp.eps")
	}
}