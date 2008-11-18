createReportAffy = function(data.eSet,data.normalized.eSet,rgghome,reportfolder=paste(rgghome,"reportoutput","/"),target,channel,bgCorrMethod,normMethod,pmcorrmethod,summarymethod,heatcov,boxlabels="colnames",pcncomp=3,pcgroup="none",maplots=TRUE){
	dir.create(reportfolder)
	file.copy(get("stylesheet",.GlobalEnv),paste(reportfolder,"style.css",sep="/"), overwrite = T)
	
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
  report = openHtmlPage(reportfile,"MIND-report - affymetrix data")
  writeLines("<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\">",report)
  writeLines("<center>",report)
  writeLines("<h1>MIND-report</h1>",report)
  writeLines("<h3>for affymetrix data</h3>",report)
  writeLines(paste("created",date(),"by MIND GUI"),report)
  writeLines("</center>",report)
  
  
  writeLines("<table border=\"0\" align=\"center\">",report)
  writeLines("<tr class=\"sectionheader\"><th>Data preparation parameter</th><th>Settings</th></tr>",report)
  writeLines(sprintf("<tr><td>Targetfile path:</td><td>%s</td></tr>",target),report)
  writeLines(sprintf("<tr><td>Background correction:</td><td>%s</td></tr>",bgCorrMethod),report)
	writeLines(sprintf("<tr><td>Normalization method:</td><td>%s</td></tr>",normMethod),report)
  writeLines(sprintf("<tr><td>PM correction:</td><td>%s</td></tr>",pmcorrmethod),report)
  writeLines(sprintf("<tr><td>Summary:</td><td>%s</td></tr>",summarymethod),report)  
  writeLines("</table>",report)
  
  
  writeLines("<table border=\"0\" align=\"center\">",report)
  
  writeLines("<tr><th colspan=\"2\"><h1>QC plots</h1></th></tr>",report)  
  
	nsteps = ifelse(maplots,8+ncol(data.eSet)[[1]]*2,8)
  
	pbar = winProgressBar(title = "MIND-report - Affymetrix data", label = "Report creation: 0% done.", min = 0, max = nsteps, initial = 0, width = 300)
	j=0
	
	
  #BOXPLOTS
  if(boxlabels == "colnames"){
    writeLines("<tr><th class=\"sectionheader\" colspan=\"2\"><h2>Boxplot</h2></th></tr>",report)
  }else{
    writeLines(sprintf("<tr><th class=\"sectionheader\" colspan=\"2\"><h2>Boxplots - labeled with covariate: %s</h2></th></tr>",boxlabels),report)
  }
  writeLines(sprintf("<tr class=\"sectionheader\"><th>Before normalization</th><th>After normalization with: %s </th></tr>",normMethod),report)
  
  #creating boxplots
  beforerange = range(exprs(data.eSet),na.rm=T,finite=T)
  afterrange = range(exprs(data.normalized.eSet),na.rm=T,finite=T)
  range = c(min(beforerange[1],afterrange[1]),max(beforerange[2],afterrange[2]))
  names = paste(reportfolder,"/","boxplotbefore",c(".pdf",".png"),sep="")
  boxlabels

  boxplotSingle(data.eSet,do.logtransform = FALSE, boxlabels=boxlabels, channel=channel,ylim=range)
  dev.print(device=pdf,names[1],width=w,height=h,pointsize=14)
  dev.print(device=png,names[2],width=wd,height=hg,pointsize=10)
  if(dev.interactive()){
	 dev.off()
  }
  
  j=j+1
	setWinProgressBar(pbar, j, label=paste("Report creation:",round(j/nsteps*100),"% done."))  
  
  names = paste(reportfolder,"/","boxplotafter",c(".pdf",".png"),sep="")

  boxplotSingle(data.normalized.eSet,do.logtransform = FALSE, boxlabels=boxlabels, channel=channel,ylim=range)
  dev.print(device=pdf,names[1],width=w,height=h,pointsize=14)
  dev.print(device=png,names[2],width=wd,height=hg,pointsize=10)
  if(dev.interactive()){
	 dev.off()
  }
  
  j=j+1
	setWinProgressBar(pbar, j, label=paste("Report creation:",round(j/nsteps*100),"% done."))
	
  writeLines("<tr><td><a href=\"boxplotbefore.pdf\"><img src=\"boxplotbefore.png\"/></a></td><td><a href=\"boxplotafter.pdf\"><img src=\"boxplotafter.png\"/></a></td></tr>",report)
  
  #MA PLOTS
  writeLines("<tr><th class=\"sectionheader\" colspan=\"2\"><h2>MA plots (for each array)</h2></th></tr>",report)
  if(maplots){
  writeLines("<tr><td colspan=\"2\"><center><a href=\"maplots.html\">Link to MAplots</a></center></td></tr>",report)
  }else{
  writeLines("<tr><td colspan=\"2\"><center>none</center></td></tr>",report)
  }
  
  #HEATMAPS
  writeLines(sprintf("<tr><th class=\"sectionheader\" colspan=\"2\"><h2>Heatmap (color bars correspond to covariate: %s)</h2></th></tr>",heatcov),report)
  writeLines(sprintf("<tr class=\"sectionheader\"><th>Before normalization</th><th>After normalization with: %s</th></tr>",normMethod),report) 
  
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
  writeLines(sprintf("<tr class=\"sectionheader\"><th>Before normalization</th><th>After normalization with: %s </th></tr>",normMethod),report)   
  #creating meansdplots
  names = paste(reportfolder,"/","meansdbefore",c(".pdf",".png"),sep="")

  plotmeanSd(data.eSet,do.logtransform=F)
  dev.print(device=pdf,names[1],width=w,height=h,pointsize=14)
  dev.print(device=png,names[2],width=wd,height=hg,pointsize=10)
  if(dev.interactive()){
	 dev.off()
  }
  
  j=j+1
	setWinProgressBar(pbar, j, label=paste("Report creation:",round(j/nsteps*100),"% done."))
  
  names = paste(reportfolder,"/","meansdafter",c(".pdf",".png"),sep="")

  plotmeanSd(data.normalized.eSet,do.logtransform=F)
  dev.print(device=pdf,names[1],width=w,height=h,pointsize=14)
  dev.print(device=png,names[2],width=wd,height=hg,pointsize=10)
  if(dev.interactive()){
	 dev.off()
  }
  
	j=j+1
	setWinProgressBar(pbar, j, label=paste("Report creation:",round(j/nsteps*100),"% done."))
	
  writeLines("<tr><td><a href=\"meansdbefore.pdf\"><img src=\"meansdbefore.png\"/></a></td><td><a href=\"meansdafter.pdf\"><img src=\"meansdafter.png\"/></a></td></tr>",report)
  
  #PRINCIPAL COMPONENTS PLOTS
  writeLines(sprintf("<tr><th class=\"sectionheader\" colspan=\"2\"><h2>Principal components (first %s) - colored with covariate: %s</h2></th></tr>",pcncomp,pcgroup),report)
  writeLines(sprintf("<tr class=\"sectionheader\"><th>Before normalization</th><th>After normalization with: %s </th></tr>",normMethod),report) 
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
  ma = openHtmlPage(maplotfile,"MIND-report - single color data")
  writeLines("<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\">",ma)
  if(maplots){
  #MA PLOTS
  writeLines("<center><a href=\"MIND-report.html\">back</a></center>",ma)
  writeLines("<table border=\"0\" align=\"center\">",ma)
  writeLines("<tr><th class=\"sectionheader\" colspan=\"3\"><h2>MA plots (for each array)</h2></th></tr>",ma)
  writeLines(sprintf("<tr class=\"sectionheader\"><th></th><th>Before normalization</th><th>After normalization with: %s </th></tr>",normMethod),ma) 
  dir.create(paste(reportfolder,"/MAplots",sep=""))
	
	#computation of plot limits
	mlim = matrix(0,ncol(exprs(data.eSet)),2)
	alim = mlim
	med = apply(exprs(data.eSet),1,median,na.rm=T)
  for (i in 1:ncol(exprs(data.eSet))){
	mlim[i,] = range(exprs(data.eSet)[,i]-med,na.rm=T)
	alim[i,] = range((exprs(data.eSet)[,i]+med)/2,na.rm=T)
	}
	xlim = range(alim)
	ylim = range(mlim)
	rm(mlim,alim,med)
	
  for (i in 1:ncol(exprs(data.eSet))){
    relnamesb = paste("MAplots/","maplot",i,"before",c(".pdf",".png"),sep="")
    namesbefore = paste(reportfolder,"/",relnamesb,sep="")

	plotMASingle(data.eSet,i,xlim=xlim,ylim=ylim,do.logtransform=FALSE)
    dev.print(device=pdf,namesbefore[1],width=w,height=h,pointsize=14)
    dev.print(device=png,namesbefore[2],width=wd,height=hg,pointsize=10)
    if(dev.interactive()){
	 dev.off()
  }
  
    j=j+1
		setWinProgressBar(pbar, j, label=paste("Report creation:",round(j/nsteps*100),"% done."))
	
    relnamesa = paste("MAplots/","maplot",i,"after",c(".pdf",".png"),sep="")
    namesafter = paste(reportfolder,"/",relnamesa,sep="")

	plotMASingle(data.normalized.eSet,i,xlim=xlim,ylim=ylim,do.logtransform=FALSE)
    dev.print(device=pdf,namesafter[1],width=w,height=h,pointsize=14)
    dev.print(device=png,namesafter[2],width=wd,height=hg,pointsize=10)
    if(dev.interactive()){
	 dev.off()
  }
	
		j=j+1
		setWinProgressBar(pbar, j, label=paste("Report creation:",round(j/nsteps*100),"% done."))
	
    writeLines(sprintf("<tr><td><h1>%s</h1></td><td><a link href=\"%s\"><img src=\"%s\"/></a></td><td><a link href=\"%s\"><img src=\"%s\"/></a></td></tr>",i,relnamesb[1],relnamesb[2],relnamesa[1],relnamesa[2]),ma)
    }
  }
  writeLines("</table>",ma)
  closeHtmlPage(ma)
  close(pbar)
	
  if(!dev.interactive()){
	dev.off()
	file.remove("temp.eps")
  }
}