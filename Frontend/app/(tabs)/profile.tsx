import { Text } from "@components";
import { Icon } from "@components/icon";
import { useContext } from "react";
import { View, Image } from "react-native";
import { SafeAreaView } from "react-native-safe-area-context";
import { ThemeContext } from "@constants/themes";

import * as VectorIcons from '@expo/vector-icons';
import { ProfilePageStyles } from "@styles/profile.style";


export default function Profile() {
  const theme = useContext(ThemeContext);
  const styles = ProfilePageStyles(theme);

  return (
    <SafeAreaView style={styles.pageContainer}>
      <View style={styles.profileSummaryContainer}>
        <View style={styles.profilePictureContainer}>
          <Image style={styles.profilePicture} source={{uri: 'https://en.m.wikipedia.org/wiki/File:Portrait_Placeholder.png'}} />
          <Icon iconType={VectorIcons.Entypo} name="plus" style={styles.addToStoryBadge} />
        </View>        
        <View style={styles.userInfoContainer}>
          <Text>Username</Text>
          <View style={styles.userStatsContainer}>
            <Text>129 Friends</Text>
            <Text>|</Text>
            <Text>3Posts</Text>
            <Text>|</Text>
            <Text>18 Pub crawls completed</Text>
          </View>
        </View>
      </View>

    </SafeAreaView>
  );
}