import { View, StyleSheet, Pressable } from 'react-native'
import { styles as barStyles } from './PubcrawlCreateBar.style'
import { CustomTheme, ThemeContext } from '@constants/themes';
import { useContext } from 'react';
import { Text } from '@components/Text';
import * as VectorIcons from '@expo/vector-icons';
import { Icon } from "@components/icon";

export function PubcrawlCreateBar () {
  const theme = useContext(ThemeContext);
  const styles = barStyles(theme);

  return(
    <View style={styles.container}>
      <Pressable style={styles.button1}>
        <Text style={styles.text}>
          Start
        </Text>
        <Icon style={styles.icon} iconType={VectorIcons.MaterialIcons} name='directions-walk' />
      </Pressable>
      <Pressable style={styles.button2}>
        <Text style={styles.text}>
          Plan
        </Text>
        <Icon style={styles.icon} iconType={VectorIcons.MaterialCommunityIcons} name='vector-polyline-edit' />
      </Pressable>
      <Pressable style={styles.button3}>
        <Text style={styles.text}>
          Browse
        </Text>
        <Icon style={styles.icon}  iconType={VectorIcons.FontAwesome5} name='route' />
      </Pressable>
      
    </View>
  )
}


