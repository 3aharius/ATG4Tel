<%@ taglib uri="http://www.atg.com/taglibs/daf/dspjspTaglib1_1"
	prefix="dsp"%>
<dsp:importbean bean="/my/handlers/GenresFormHandler" />
<dsp:importbean bean="/my/model/Genre" />
<dsp:importbean bean="/atg/dynamo/droplet/ForEach" />
<dsp:importbean bean="/atg/targeting/RepositoryLookup" />
<dsp:page>
	<h1>Subscribe me for the news</h1>
	<dsp:form method="post">
		<dsp:input type="hidden" bean="GenresFormHandler.successURL"
			value="info.jsp" />
		<dsp:input bean="GenresFormHandler.firstName" type="text" />
		<dsp:input bean="GenresFormHandler.lastName" type="text" />
		<dsp:input bean="GenresFormHandler.email" type="text" />

		<dsp:droplet name="ForEach">
			<dsp:param name="array" bean="GenresFormHandler.genres" />
			<dsp:param name="elementName" value="genre" />
			<dsp:oparam name="output">
				<dsp:input type="checkbox" bean="GenresFormHandler.selectedGenres"
					value="genre.id" checked="false">
					<dsp:valueof param="genre.title" />
				</dsp:input>
			</dsp:oparam>
		</dsp:droplet>

		<dsp:input id="submitFormInput" bean="GenresFormHandler.Subscrbe"
			type="submit" />
	</dsp:form>
</dsp:page>
