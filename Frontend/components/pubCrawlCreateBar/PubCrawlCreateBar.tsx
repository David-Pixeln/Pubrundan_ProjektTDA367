import { View, Pressable } from 'react-native'
import { styles as barStyles } from './PubCrawlCreateBar.style'
import { ThemeContext } from '@constants/themes';
import { useContext } from 'react';
import { Text } from '@components/Text';
import { Button } from '@components/buttons';
import * as VectorIcons from '@expo/vector-icons';
import { Icon } from "@components/icon";


export function PubCrawlCreateBar () {
  const theme = useContext(ThemeContext);
  const styles = barStyles(theme);
  const hitSlop = { bottom: 28 };


  return(
    <View style={styles.container}>
      <View style={styles.button}>
        <Button
          hitSlop={hitSlop}
          style={styles.iconWrapper}
          pressedStyle={styles.iconWrapperPressed}
        >
          <Icon iconType={VectorIcons.MaterialIcons} name='directions-walk' />
        </Button>
        <Text style={styles.text}>Start</Text>
      </View>

      <View style={styles.button}>
        <Button 
          hitSlop={hitSlop}
          style={styles.iconWrapper}
          pressedStyle={styles.iconWrapperPressed}
        >
          <Icon iconType={VectorIcons.MaterialCommunityIcons} name='vector-polyline-edit' />
        </Button>
        <Text style={styles.text}>Plan</Text>
      </View>

      <View style={styles.button}>
        <Button 
          hitSlop={hitSlop}
          style={styles.iconWrapper}
          pressedStyle={styles.iconWrapperPressed}
        >
          <Icon iconType={VectorIcons.FontAwesome5} name='route' size={24} />
        </Button>
        <Text style={styles.text}>Start</Text>
      </View>
    </View>
  )
}
