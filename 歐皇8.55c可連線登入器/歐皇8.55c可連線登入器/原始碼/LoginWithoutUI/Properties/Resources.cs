using System;
using System.CodeDom.Compiler;
using System.ComponentModel;
using System.Diagnostics;
using System.Globalization;
using System.Resources;
using System.Runtime.CompilerServices;

namespace LoginWithoutUI.Properties
{
	// Token: 0x02000012 RID: 18
	[GeneratedCode("System.Resources.Tools.StronglyTypedResourceBuilder", "4.0.0.0")]
	[CompilerGenerated]
	[DebuggerNonUserCode]
	internal class Resources
	{
		// Token: 0x06000063 RID: 99 RVA: 0x00002203 File Offset: 0x00000403
		internal Resources()
		{
		}

		// Token: 0x17000001 RID: 1
		// (get) Token: 0x06000064 RID: 100 RVA: 0x00002834 File Offset: 0x00000A34
		[EditorBrowsable(EditorBrowsableState.Advanced)]
		internal static ResourceManager ResourceManager
		{
			get
			{
				if (Resources.resourceManager_0 == null)
				{
					ResourceManager resourceManager = new ResourceManager("LoginWithoutUI.Properties.Resources", typeof(Resources).Assembly);
					Resources.resourceManager_0 = resourceManager;
				}
				return Resources.resourceManager_0;
			}
		}

		// Token: 0x17000002 RID: 2
		// (get) Token: 0x06000065 RID: 101 RVA: 0x00002874 File Offset: 0x00000A74
		// (set) Token: 0x06000066 RID: 102 RVA: 0x00002270 File Offset: 0x00000470
		[EditorBrowsable(EditorBrowsableState.Advanced)]
		internal static CultureInfo Culture
		{
			get
			{
				return Resources.cultureInfo_0;
			}
			set
			{
				Resources.cultureInfo_0 = value;
			}
		}

		// Token: 0x04000051 RID: 81
		private static ResourceManager resourceManager_0;

		// Token: 0x04000052 RID: 82
		private static CultureInfo cultureInfo_0;
	}
}
