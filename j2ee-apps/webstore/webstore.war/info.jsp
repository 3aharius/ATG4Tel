<%@ taglib uri="http://www.atg.com/taglibs/daf/dspjspTaglib1_1"
	prefix="dsp"%>
<dsp:importbean bean="/my/droplets/UserLookup" />
<dsp:importbean bean="/atg/dynamo/droplet/ForEach" />
<dsp:page>
	<h1>Congratulations!</h1>
	<dsp:droplet name="/atg/commerce/catalog/ProductLookup">
		<dsp:param name="id" param="uid" />
		<dsp:setvalue param="user" paramvalue="element" />
		<dsp:oparam name="output">
			<dsp:valueof param="user.firstName" />
			<dsp:valueof param="user.lastName" />
			<dsp:valueof param="user.email" />
			<dsp:valueof param="user.suscriptionDate" />
			<dsp:droplet name="ForEach">
				<dsp:param name="array" param="user.genres" />
				<dsp:param name="elementName" value="genre" />
				<dsp:oparam name="output">
					<dsp:valueof param="genre.title" />
				</dsp:oparam>
			</dsp:droplet>
		</dsp:oparam>
	</dsp:droplet>
</dsp:page>